package BUS;

import DAO.NganhDAO;
import DAO.NguyenVongXetTuyenDAO;
import entity.Nganh;
import entity.NguyenVongXetTuyen;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class XetTuyenBUS {

    public static final String KQ_TRUNG_TUYEN = "Trúng tuyển";
    public static final String KQ_KHONG_TRUNG = "Không trúng tuyển";
    public static final String KQ_KHONG_DAT_NV = "Không đạt nguyện vọng";
    public static final String KQ_CHO_XET = "Chờ xét";

    private final NguyenVongXetTuyenDAO nvDAO = new NguyenVongXetTuyenDAO();
    private final NganhDAO nganhDAO = new NganhDAO();

    // =========================================================================
    // PHƯƠNG THỨC CHÍNH
    // =========================================================================

    /**
     * Chạy xét tuyển toàn bộ theo thuật toán nguyện vọng ưu tiên.
     *
     * Thuật toán:
     * 1. Load toàn bộ NV đã có điểm ĐXT.
     * 2. Reset kết quả cũ về "Chờ xét".
     * 3. Load chỉ tiêu từng ngành.
     * 4. Nhóm NV theo thí sinh, sort NV theo nv_tt tăng dần.
     * 5. Sort thí sinh theo điểm cao nhất giảm dần (điểm cao được xét trước).
     * 6. Vòng ngoài: duyệt từng thí sinh.
     * Vòng trong: duyệt NV theo thứ tự ưu tiên.
     * - Ngành còn chỉ tiêu → Trúng tuyển, giảm chỉ tiêu, break.
     * - Hết chỉ tiêu → Không đạt NV này, thử NV tiếp.
     * Hết NV mà không trúng → "Không trúng tuyển".
     * 7. Batch update vào DB.
     */
    public XetTuyenResult runXetTuyen() {

        // 1. Load dữ liệu
        List<NguyenVongXetTuyen> tatCaNV = nvDAO.getAll().stream()
                .filter(nv -> nv.getDiemXetTuyen() != null)
                .collect(Collectors.toList());

        if (tatCaNV.isEmpty()) {
            return new XetTuyenResult(0, 0, new HashMap<>(),
                    "Không có nguyện vọng nào đã tính điểm.");
        }

        // 2. Reset kết quả cũ
        tatCaNV.forEach(nv -> nv.setNvKetQua(KQ_CHO_XET));

        // 3. Load chỉ tiêu từng ngành
        Map<String, Integer> chiTieuConLai = new HashMap<>();
        for (Nganh ng : nganhDAO.getAllIndustry()) {
            int ct = ng.getN_chitieu() != null ? ng.getN_chitieu() : 0;
            chiTieuConLai.put(ng.getManganh(), ct);
        }

        // 4. Nhóm NV theo thí sinh, sort theo nv_tt tăng dần
        Map<String, List<NguyenVongXetTuyen>> nvTheoCccd = tatCaNV.stream()
                .collect(Collectors.groupingBy(NguyenVongXetTuyen::getNnCccd));

        nvTheoCccd.values()
                .forEach(ds -> ds.sort(Comparator.comparingInt(nv -> nv.getNvThuTu() != null ? nv.getNvThuTu() : 999)));

        // 5. Sort thí sinh theo điểm cao nhất giảm dần
        List<String> danhSachCccd = new ArrayList<>(nvTheoCccd.keySet());
        danhSachCccd.sort((a, b) -> diemCaoNhat(nvTheoCccd.get(b)).compareTo(diemCaoNhat(nvTheoCccd.get(a))));

        // 6. Thuật toán xét tuyển chính
        int tongTrung = 0;
        Map<String, Integer> soTrungTheoNganh = new HashMap<>();

        for (String cccd : danhSachCccd) {
            List<NguyenVongXetTuyen> dsNV = nvTheoCccd.get(cccd);
            boolean daTrung = false;

            for (NguyenVongXetTuyen nv : dsNV) {
                String manganh = nv.getNvManganh();
                int conLai = chiTieuConLai.getOrDefault(manganh, 0);

                if (conLai > 0) {
                    nv.setNvKetQua(KQ_TRUNG_TUYEN);
                    chiTieuConLai.put(manganh, conLai - 1);
                    soTrungTheoNganh.merge(manganh, 1, Integer::sum);
                    tongTrung++;
                    daTrung = true;
                    break;
                } else {
                    nv.setNvKetQua(KQ_KHONG_DAT_NV);
                }
            }

            if (!daTrung) {
                dsNV.get(dsNV.size() - 1).setNvKetQua(KQ_KHONG_TRUNG);
            }
        }

        // 7. Batch update DB
        nvDAO.updateKetQuaBatch(tatCaNV);

        int tongKhong = danhSachCccd.size() - tongTrung;
        String baoCao = String.format(
                "Xét tuyển hoàn tất: %d thí sinh trúng / %d không trúng tuyển.",
                tongTrung, tongKhong);

        return new XetTuyenResult(tongTrung, tongKhong, soTrungTheoNganh, baoCao);
    }

    // =========================================================================
    // RESET
    // =========================================================================

    /** Reset toàn bộ kết quả về "Chờ xét" để chạy lại. */
    public void resetKetQua() {
        List<NguyenVongXetTuyen> tatCa = nvDAO.getAll();
        tatCa.forEach(nv -> nv.setNvKetQua(KQ_CHO_XET));
        nvDAO.updateKetQuaBatch(tatCa);
    }

    // =========================================================================
    // THỐNG KÊ
    // =========================================================================

    /** Danh sách thí sinh trúng tuyển của 1 ngành, sort điểm giảm dần. */
    public List<NguyenVongXetTuyen> getDanhSachTrungTuyen(String manganh) {
        return nvDAO.getByManganh(manganh).stream()
                .filter(nv -> KQ_TRUNG_TUYEN.equals(nv.getNvKetQua()))
                .collect(Collectors.toList());
    }

    /** Danh sách thí sinh không trúng tuyển bất kỳ ngành nào. */
    public List<NguyenVongXetTuyen> getDanhSachKhongTrung() {
        return nvDAO.getAll().stream()
                .filter(nv -> KQ_KHONG_TRUNG.equals(nv.getNvKetQua()))
                .collect(Collectors.toList());
    }

    // =========================================================================
    // TIỆN ÍCH
    // =========================================================================

    private BigDecimal diemCaoNhat(List<NguyenVongXetTuyen> dsNV) {
        return dsNV.stream()
                .map(NguyenVongXetTuyen::getDiemXetTuyen)
                .filter(Objects::nonNull)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    // =========================================================================
    // INNER CLASS — kết quả trả về
    // =========================================================================

    public static class XetTuyenResult {
        public final int soTrungTuyen;
        public final int soKhongTrung;
        public final Map<String, Integer> soTrungTheoNganh;
        public final String baoCao;

        public XetTuyenResult(int soTrungTuyen, int soKhongTrung,
                Map<String, Integer> soTrungTheoNganh, String baoCao) {
            this.soTrungTuyen = soTrungTuyen;
            this.soKhongTrung = soKhongTrung;
            this.soTrungTheoNganh = soTrungTheoNganh;
            this.baoCao = baoCao;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(baoCao).append("\n");
            soTrungTheoNganh.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(e -> sb.append(
                            String.format("  %s: %d trúng\n", e.getKey(), e.getValue())));
            return sb.toString();
        }
    }
}