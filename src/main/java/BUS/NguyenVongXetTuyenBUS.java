package BUS;

import DAO.DiemXetTuyenDAO;
import DAO.NganhToHopDAO;
import DAO.NguyenVongXetTuyenDAO;
import entity.DiemThiXetTuyen;
import entity.Nganh;
import entity.NganhToHop;
import entity.NguyenVongXetTuyen;
import DAO.NganhDAO;

import java.math.BigDecimal;
import java.util.*;

public class NguyenVongXetTuyenBUS {

    private final NguyenVongXetTuyenDAO dao = new NguyenVongXetTuyenDAO();
    private final Scorecalculationservice scoreService = new Scorecalculationservice();
    private final DiemXetTuyenDAO diemDAO = new DiemXetTuyenDAO();
    private final NganhToHopDAO nganhToHopDAO = new NganhToHopDAO();
    private final NganhDAO nganhDAO = new NganhDAO();

    // =========================================================================
    // CRUD cơ bản
    // =========================================================================

    public List<NguyenVongXetTuyen> getAll() {
        return dao.getAll();
    }

    public List<NguyenVongXetTuyen> getByPage(int page, int pageSize) {
        return dao.getByPage(page, pageSize);
    }

    public long countAll() {
        return dao.countAll();
    }

    public int totalPages(int pageSize) {
        return (int) Math.ceil((double) countAll() / pageSize);
    }

    public List<NguyenVongXetTuyen> getByCccd(String cccd) {
        return dao.getByCccd(cccd.trim());
    }

    public List<NguyenVongXetTuyen> getByManganh(String manganh) {
        return dao.getByManganh(manganh.trim());
    }

    public NguyenVongXetTuyen getById(int id) {
        return dao.getById(id);
    }

    // =========================================================================
    // Thêm mới nguyện vọng + tính điểm ngay
    // =========================================================================

    /**
     * Thêm 1 nguyện vọng mới:
     * - Sinh nv_keys
     * - Tìm NganhToHop tương ứng
     * - Tìm DiemThiXetTuyen theo cccd + phương thức
     * - Gọi Scorecalculationservice tính điểm
     * - Lưu vào DB
     *
     * @param cccd       CCCD thí sinh
     * @param manganh    Mã ngành đăng ký
     * @param matohop    Mã tổ hợp
     * @param phuongThuc THPT | VSAT | DGNL
     * @param thuTu      Thứ tự nguyện vọng (1, 2, 3...)
     * @param mducuutien Mức điểm ưu tiên Điều 7 (0 nếu không có)
     * @return kết quả lưu thành công hay không
     */
    public boolean themNguyenVong(String cccd, String manganh, String matohop,
            String phuongThuc, int thuTu, BigDecimal mducuutien) {
        // 1. Kiểm tra trùng (cùng cccd + ngành + tổ hợp + phương thức)
        String nvKeys = buildNvKeys(cccd, manganh, matohop, phuongThuc);
        if (dao.getByKeys(nvKeys) != null) {
            throw new IllegalArgumentException("Nguyện vọng này đã tồn tại!");
        }

        // 2. Tìm NganhToHop
        NganhToHop nganhToHop = nganhToHopDAO.getByManganhAndMatohop(manganh, matohop);
        if (nganhToHop == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy ngành-tổ hợp: " + manganh + " / " + matohop);
        }

        // 3. Tìm điểm thi của thí sinh theo phương thức
        DiemThiXetTuyen diemThi = diemDAO.getByCccdAndPhuongThuc(cccd, phuongThuc);
        if (diemThi == null) {
            throw new IllegalArgumentException(
                    "Thí sinh " + cccd + " chưa có điểm " + phuongThuc);
        }

        // 4. Tạo đối tượng nguyện vọng
        NguyenVongXetTuyen nv = new NguyenVongXetTuyen();
        nv.setNnCccd(cccd);
        nv.setNvManganh(manganh);
        nv.setNvThuTu(thuTu);
        nv.setPhuongThuc(phuongThuc);
        nv.setToHopMon(matohop);
        nv.setNvKeys(nvKeys);

        // 5. Tính điểm
        scoreService.tinhDiemNguyenVong(nv, diemThi, nganhToHop, mducuutien);

        // 6. Lưu
        return dao.save(nv);
    }

    // =========================================================================
    // Cập nhật nguyện vọng (chỉ cho phép sửa thứ tự)
    // =========================================================================

    public boolean capNhatThuTu(int idnv, int thuTuMoi) {
        NguyenVongXetTuyen nv = dao.getById(idnv);
        if (nv == null)
            return false;
        nv.setNvThuTu(thuTuMoi);
        return dao.update(nv);
    }

    // =========================================================================
    // Xóa
    // =========================================================================

    public boolean xoa(int id) {
        return dao.delete(id);
    }

    // =========================================================================
    // Tính lại điểm 1 nguyện vọng (dùng khi cần re-calculate)
    // =========================================================================

    public boolean tinhLaiDiem(int idnv, BigDecimal mducuutien) {
        NguyenVongXetTuyen nv = dao.getById(idnv);
        if (nv == null)
            return false;

        NganhToHop nganhToHop = nganhToHopDAO.getByManganhAndMatohop(
                nv.getNvManganh(), nv.getToHopMon());
        DiemThiXetTuyen diemThi = diemDAO.getByCccdAndPhuongThuc(
                nv.getNnCccd(), nv.getPhuongThuc());

        if (nganhToHop == null || diemThi == null)
            return false;

        scoreService.tinhDiemNguyenVong(nv, diemThi, nganhToHop, mducuutien);
        return dao.update(nv);
    }

    // =========================================================================
    // XÉT TUYỂN HÀNG LOẠT – chạy cho toàn bộ ngành
    // =========================================================================

    /**
     * Thuật toán xét tuyển:
     * 1. Với mỗi ngành, lấy danh sách thí sinh đã có điểm xét tuyển
     * 2. Sort giảm dần theo diem_xettuyen
     * 3. Cắt theo chỉ tiêu (n_chitieu) của ngành
     * 4. Thí sinh trong chỉ tiêu → "Trúng tuyển", còn lại → "Không trúng tuyển"
     * 5. Cập nhật batch vào DB
     */
    public void runXetTuyen() {
        List<Nganh> danhSachNganh = nganhDAO.getAllIndustry();
        List<NguyenVongXetTuyen> toUpdate = new ArrayList<>();

        for (Nganh nganh : danhSachNganh) {
            int chiTieu = nganh.getN_chitieu() != null ? nganh.getN_chitieu() : 0;
            if (chiTieu <= 0)
                continue;

            // Lấy danh sách đã sort theo điểm giảm dần
            List<NguyenVongXetTuyen> dsNganh = dao.getByManganh(nganh.getManganh());

            // Set đã trúng tuyển (tránh 1 thí sinh trúng tuyển > 1 ngành theo nguyện vọng
            // ưu tiên)
            // Trong bài toán này, mỗi thí sinh chỉ trúng tuyển nguyện vọng ưu tiên cao nhất
            // Để đơn giản: cắt theo chỉ tiêu, trúng nếu nằm trong top
            int soTrung = 0;
            for (NguyenVongXetTuyen nv : dsNganh) {
                if (soTrung < chiTieu) {
                    nv.setNvKetQua("Trúng tuyển");
                    soTrung++;
                } else {
                    nv.setNvKetQua("Không trúng tuyển");
                }
                toUpdate.add(nv);
            }
        }

        // Batch update
        if (!toUpdate.isEmpty()) {
            dao.updateKetQuaBatch(toUpdate);
        }
    }

    // =========================================================================
    // Tính lại điểm hàng loạt cho tất cả nguyện vọng chưa có điểm
    // (dùng sau khi import dữ liệu)
    // =========================================================================

    public int tinhDiemHangLoat(BigDecimal mducuutienDefault) {
        List<NguyenVongXetTuyen> tatCa = dao.getAll();
        List<NguyenVongXetTuyen> daCapNhat = new ArrayList<>();
        int thanhCong = 0;

        for (NguyenVongXetTuyen nv : tatCa) {
            // Chỉ tính lại những nguyện vọng chưa có điểm
            if (nv.getDiemXetTuyen() != null)
                continue;

            try {
                NganhToHop nganhToHop = nganhToHopDAO.getByManganhAndMatohop(
                        nv.getNvManganh(), nv.getToHopMon());
                DiemThiXetTuyen diemThi = diemDAO.getByCccdAndPhuongThuc(
                        nv.getNnCccd(), nv.getPhuongThuc());

                if (nganhToHop == null || diemThi == null)
                    continue;

                scoreService.tinhDiemNguyenVong(nv, diemThi, nganhToHop, mducuutienDefault);
                daCapNhat.add(nv);
                thanhCong++;
            } catch (Exception e) {
                System.err.println("[BUS] Lỗi tính điểm NV id=" + nv.getIdnv() + ": " + e.getMessage());
            }
        }

        if (!daCapNhat.isEmpty()) {
            dao.updateKetQuaBatch(daCapNhat);
        }

        return thanhCong;
    }

    // =========================================================================
    // Tiện ích
    // =========================================================================

    /**
     * Tạo khóa duy nhất cho nguyện vọng.
     * Format: cccd_manganh_matohop_phuongthuc
     */
    public static String buildNvKeys(String cccd, String manganh, String matohop, String phuongThuc) {
        return cccd + "_" + manganh + "_" + matohop + "_" + phuongThuc;
    }
}