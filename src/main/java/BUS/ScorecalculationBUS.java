package BUS;

import DAO.BangQuyDoiDAO;
import DAO.NganhToHopDAO;
import DAO.DiemCongXetTuyenDAO;
import entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * ScoreCalculationService
 *
 * Thực hiện tính điểm xét tuyển theo đúng thứ tự:
 * Bước 1: Tính ĐTHXT – điểm tổ hợp quy đổi về thang 30 của THPT
 * Bước 2: Tính ĐTHGXT – điểm tổ hợp gốc (quy đổi tổ hợp theo bảng dolech)
 * Bước 3: Tính ĐC – điểm cộng (lấy từ bảng xt_diemcongxetuyen)
 * Bước 4: Tính ĐƯT – điểm ưu tiên (có 2 công thức tùy ĐTHGXT + ĐC)
 * Bước 5: Tính ĐXT – điểm xét tuyển cuối = ĐTHGXT + ĐC + ĐƯT (tối đa 30)
 */

public class ScorecalculationBUS {

    // -------------------------------------------------------------------------
    // Hằng số phương thức
    // -------------------------------------------------------------------------
    public static final String PT_THPT = "THPT";
    public static final String PT_VSAT = "VSAT";
    public static final String PT_DGNL = "DGNL";

    private final BangQuyDoiDAO bangQuyDoiDAO = new BangQuyDoiDAO();
    private final DiemCongXetTuyenDAO diemCongDAO = new DiemCongXetTuyenDAO();

    // =========================================================================
    // PHƯƠNG THỨC CHÍNH – gọi từ bên ngoài
    // =========================================================================

    /**
     * Tính toàn bộ điểm cho một nguyện vọng rồi điền vào object truyền vào.
     *
     * @param nv         NguyenVongXetTuyen cần tính (tt_phuongthuc & toHopMon phải
     *                   có sẵn)
     * @param diemThi    DiemThiXetTuyen của thí sinh (cùng phương thức)
     * @param nganhToHop NganhToHop tương ứng (manganh + matohop của nguyện vọng)
     * @param mducuutien Mức điểm ưu tiên theo Điều 7 Quy chế tuyển sinh
     */
    public void tinhDiemNguyenVong(NguyenVongXetTuyen nv,
            DiemThiXetTuyen diemThi,
            NganhToHop nganhToHop,
            BigDecimal mducuutien) {

        String phuongThuc = nv.getPhuongThuc(); // "THPT" | "VSAT" | "DGNL"

        // --- Bước 1: ĐTHXT ---------------------------------------------------
        BigDecimal dthxt = tinhDTHXT(phuongThuc, diemThi, nganhToHop);
        nv.setDiemThxt(dthxt);

        // --- Bước 2: ĐTHGXT --------------------------------------------------
        BigDecimal dthgxt = tinhDTHGXT(phuongThuc, dthxt, nganhToHop);
        // (lưu tạm vào diemUtqd để không cần thêm field, hoặc bạn có thể thêm field
        // riêng)
        // Ở đây ta dùng biến cục bộ, kết quả cuối vẫn đi vào ĐXT

        // --- Bước 3: ĐC ------------------------------------------------------
        BigDecimal dc = layDiemCong(nv.getNnCccd(), nv.getNvManganh(), nganhToHop.getMatohop(), phuongThuc);
        nv.setDiemCong(dc);

        // --- Bước 4: ĐƯT -----------------------------------------------------
        BigDecimal dut = tinhDUT(dthgxt, dc, mducuutien);
        nv.setDiemUtqd(dut);

        // --- Bước 5: ĐXT -----------------------------------------------------
        BigDecimal dxt = dthgxt.add(dc).add(dut);
        // Tối đa 30 điểm
        if (dxt.compareTo(BigDecimal.valueOf(30)) > 0) {
            dxt = BigDecimal.valueOf(30);
        }
        nv.setDiemXetTuyen(dxt.setScale(2, RoundingMode.HALF_UP));
    }

    // =========================================================================
    // BƯỚC 1 – Tính ĐTHXT
    // =========================================================================

    /**
     * Tính điểm tổ hợp xét tuyển đã quy đổi tương đương về phương thức THPT.
     *
     * - THPT & VSAT : ĐTHXT = [(d1*w1 + d2*w2 + d3*w3) / W] * 3
     * (với VSAT: d1,d2,d3 đã được quy về thang 10 qua bảng quy đổi)
     * - DGNL : ĐTHXT = điểm ĐGNL đã quy về thang 30 qua bảng bách phân vị
     */
    public BigDecimal tinhDTHXT(String phuongThuc,
            DiemThiXetTuyen diemThi,
            NganhToHop nganhToHop) {
        switch (phuongThuc.toUpperCase()) {
            case PT_THPT:
                return tinhDTHXT_THPT(diemThi, nganhToHop);
            case PT_VSAT:
                return tinhDTHXT_VSAT(diemThi, nganhToHop);
            case PT_DGNL:
                return tinhDTHXT_DGNL(diemThi);
            default:
                throw new IllegalArgumentException("Phương thức không hợp lệ: " + phuongThuc);
        }
    }

    /** THPT: ĐTHXT = [(d1*w1 + d2*w2 + d3*w3) / W] * 3 */
    private BigDecimal tinhDTHXT_THPT(DiemThiXetTuyen diemThi, NganhToHop nth) {
        BigDecimal d1 = layDiemMon(diemThi, nth.getTh_mon1());
        BigDecimal d2 = layDiemMon(diemThi, nth.getTh_mon2());
        BigDecimal d3 = layDiemMon(diemThi, nth.getTh_mon3());

        int w1 = nth.getHsmon1() != null ? nth.getHsmon1() : 1;
        int w2 = nth.getHsmon2() != null ? nth.getHsmon2() : 1;
        int w3 = nth.getHsmon3() != null ? nth.getHsmon3() : 1;
        int W = w1 + w2 + w3;

        BigDecimal tongCo = d1.multiply(BigDecimal.valueOf(w1))
                .add(d2.multiply(BigDecimal.valueOf(w2)))
                .add(d3.multiply(BigDecimal.valueOf(w3)));

        // [(d1*w1 + d2*w2 + d3*w3) / W] * 3
        return tongCo.divide(BigDecimal.valueOf(W), 6, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(3))
                .setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * VSAT: giống THPT nhưng mỗi điểm môn phải được quy về thang 10
     * qua bảng bách phân vị (xt_bangquydoi, d_phuongthuc = 'VSAT').
     */
    private BigDecimal tinhDTHXT_VSAT(DiemThiXetTuyen diemThi, NganhToHop nth) {
        BigDecimal d1Raw = layDiemMon(diemThi, nth.getTh_mon1());
        BigDecimal d2Raw = layDiemMon(diemThi, nth.getTh_mon2());
        BigDecimal d3Raw = layDiemMon(diemThi, nth.getTh_mon3());

        // Quy từng môn VSAT về thang 10
        BigDecimal d1 = quyDoiDiemBachPhanVi(PT_VSAT, nth.getTh_mon1(), d1Raw);
        BigDecimal d2 = quyDoiDiemBachPhanVi(PT_VSAT, nth.getTh_mon2(), d2Raw);
        BigDecimal d3 = quyDoiDiemBachPhanVi(PT_VSAT, nth.getTh_mon3(), d3Raw);

        int w1 = nth.getHsmon1() != null ? nth.getHsmon1() : 1;
        int w2 = nth.getHsmon2() != null ? nth.getHsmon2() : 1;
        int w3 = nth.getHsmon3() != null ? nth.getHsmon3() : 1;
        int W = w1 + w2 + w3;

        BigDecimal tongCo = d1.multiply(BigDecimal.valueOf(w1))
                .add(d2.multiply(BigDecimal.valueOf(w2)))
                .add(d3.multiply(BigDecimal.valueOf(w3)));

        return tongCo.divide(BigDecimal.valueOf(W), 6, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(3))
                .setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * ĐGNL: ĐTHXT = điểm ĐGNL đã quy về thang 30 qua bảng bách phân vị.
     * Điểm thô ĐGNL lấy từ trường NL1 (điểm ĐGNL của ĐHQG TP.HCM).
     */
    private BigDecimal tinhDTHXT_DGNL(DiemThiXetTuyen diemThi) {
        BigDecimal diemDGNL = diemThi.getNL1() != null ? diemThi.getNL1() : BigDecimal.ZERO;
        // Quy điểm ĐGNL về thang 30 qua bảng bách phân vị
        return quyDoiDiemBachPhanVi(PT_DGNL, null, diemDGNL);
    }

    // =========================================================================
    // BƯỚC 2 – Tính ĐTHGXT (quy về tổ hợp gốc của ngành)
    // =========================================================================

    /**
     * Tính điểm tổ hợp gốc xét tuyển.
     *
     * - VSAT & THPT : ĐTHGXT = ĐTHXT - dolech (dolech lấy từ xt_nganh_tohop)
     * (dolech = 0 nếu tổ hợp đã là tổ hợp gốc hoặc không có trong bảng)
     * - DGNL : ĐTHGXT = ĐTHXT (không cần quy đổi tổ hợp)
     */
    public BigDecimal tinhDTHGXT(String phuongThuc,
            BigDecimal dthxt,
            NganhToHop nganhToHop) {
        if (PT_DGNL.equalsIgnoreCase(phuongThuc)) {
            return dthxt;
        }

        // dolech đã được lưu trong bảng xt_nganh_tohop
        BigDecimal dolech = nganhToHop.getDolech() != null
                ? nganhToHop.getDolech()
                : BigDecimal.ZERO;

        // ĐTHGXT = ĐTHXT - dolech
        return dthxt.subtract(dolech).setScale(4, RoundingMode.HALF_UP);
    }

    // =========================================================================
    // BƯỚC 3 – Lấy ĐC (điểm cộng)
    // =========================================================================

    /**
     * Lấy tổng điểm cộng của thí sinh cho ngành + tổ hợp + phương thức.
     * Tổng ĐC không vượt quá 3 điểm (thang 30).
     */
    public BigDecimal layDiemCong(String cccd, String manganh, String matohop, String phuongthuc) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT d.diemTong FROM DiemCongXetTuyen d "
                    + "WHERE d.tsCccd = :cccd "
                    + "  AND d.manganh = :ma "
                    + "  AND d.matohop = :tohop "
                    + "  AND d.phuongthuc = :pt";

            Query<BigDecimal> q = session.createQuery(hql, BigDecimal.class);
            q.setParameter("cccd", cccd);
            q.setParameter("ma", manganh);
            q.setParameter("tohop", matohop);
            q.setParameter("pt", phuongthuc);

            BigDecimal dc = q.uniqueResult();
            if (dc == null)
                dc = BigDecimal.ZERO;

            // Tối đa 3 điểm
            return dc.min(BigDecimal.valueOf(3)).setScale(2, RoundingMode.HALF_UP);

        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    // =========================================================================
    // BƯỚC 4 – Tính ĐƯT (điểm ưu tiên)
    // =========================================================================

    /**
     * Tính điểm ưu tiên theo 2 công thức:
     *
     * Nếu (ĐTHGXT + ĐC) < 22,5 : ĐƯT = MĐƯT
     * Nếu (ĐTHGXT + ĐC) >= 22,5 : ĐƯT = [(30 - ĐTHGXT - ĐC) / 7,5] × MĐƯT
     *
     * @param dthgxt     Điểm tổ hợp gốc xét tuyển
     * @param dc         Điểm cộng
     * @param mducuutien Mức điểm ưu tiên (MĐƯT) theo Điều 7
     */
    public BigDecimal tinhDUT(BigDecimal dthgxt, BigDecimal dc, BigDecimal mducuutien) {
        if (mducuutien == null || mducuutien.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal tong = dthgxt.add(dc);

        if (tong.compareTo(BigDecimal.valueOf(22.5)) < 0) {
            // ĐƯT = MĐƯT
            return mducuutien.setScale(2, RoundingMode.HALF_UP);
        } else {
            // ĐƯT = [(30 - ĐTHGXT - ĐC) / 7,5] × MĐƯT
            BigDecimal tu = BigDecimal.valueOf(30).subtract(dthgxt).subtract(dc);
            BigDecimal mau = BigDecimal.valueOf(7.5);
            BigDecimal dut = tu.divide(mau, 6, RoundingMode.HALF_UP).multiply(mducuutien);
            // ĐƯT không âm
            return dut.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
        }
    }

    // =========================================================================
    // TIỆN ÍCH – Quy đổi điểm qua bảng bách phân vị (xt_bangquydoi)
    // =========================================================================

    /**
     * Quy đổi điểm x thuộc [a,b] của phương thức phuongThuc (và môn dMon)
     * về thang điểm THPT theo công thức nội suy tuyến tính:
     *
     * y = c + [(x - a) / (b - a)] × (d - c)
     *
     * Trong đó [a,b] lấy từ (d_diema, d_diemb) và [c,d] lấy từ (d_diemc, d_diemd)
     * trong bảng xt_bangquydoi tương ứng.
     */
    public BigDecimal quyDoiDiemBachPhanVi(String phuongThuc, String dMon, BigDecimal diemX) {
        if (diemX == null)
            return BigDecimal.ZERO;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Tìm dòng quy đổi phù hợp: x nằm trong [d_diema, d_diemb]
            String hql;
            Query<BangQuyDoi> query;

            if (dMon != null) {
                hql = "FROM BangQuyDoi b "
                        + "WHERE b.dPhuongThuc = :pt "
                        + "  AND b.dMon = :mon "
                        + "  AND b.dDiemA <= :x "
                        + "  AND b.dDiemB >= :x "
                        + "ORDER BY b.dDiemA DESC";
                query = session.createQuery(hql, BangQuyDoi.class);
                query.setParameter("pt", phuongThuc.toUpperCase());
                query.setParameter("mon", dMon.toUpperCase());
                query.setParameter("x", diemX);
            } else {
                // ĐGNL không có môn cụ thể
                hql = "FROM BangQuyDoi b "
                        + "WHERE b.dPhuongThuc = :pt "
                        + "  AND b.dDiemA <= :x "
                        + "  AND b.dDiemB >= :x "
                        + "ORDER BY b.dDiemA DESC";
                query = session.createQuery(hql, BangQuyDoi.class);
                query.setParameter("pt", phuongThuc.toUpperCase());
                query.setParameter("x", diemX);
            }

            query.setMaxResults(1);
            BangQuyDoi row = query.uniqueResult();

            if (row == null) {
                // Không tìm thấy vùng phù hợp → trả về nguyên gốc (giới hạn 0-10 hoặc 0-30)
                return diemX;
            }

            BigDecimal a = row.getdDiemA();
            BigDecimal b = row.getdDiemB();
            BigDecimal c = row.getdDiemC();
            BigDecimal d = row.getdDiemD();

            if (b.compareTo(a) == 0) {
                // Tránh chia 0
                return c;
            }

            // y = c + [(x - a) / (b - a)] * (d - c)
            BigDecimal y = c.add(
                    diemX.subtract(a)
                            .divide(b.subtract(a), 6, RoundingMode.HALF_UP)
                            .multiply(d.subtract(c)));

            return y.setScale(4, RoundingMode.HALF_UP);

        } catch (Exception e) {
            e.printStackTrace();
            return diemX;
        }
    }

    // =========================================================================
    // TIỆN ÍCH – Lấy điểm môn từ DiemThiXetTuyen theo mã môn
    // =========================================================================

    /**
     * Ánh xạ mã môn (TO, LI, HO, SI, SU, DI, VA, N1_THI, N1_CC, CNCN, CNNN,
     * TI, KTPL, NL1, NK1, NK2) sang field tương ứng trong DiemThiXetTuyen.
     */
    public BigDecimal layDiemMon(DiemThiXetTuyen d, String maMon) {
        if (maMon == null || d == null)
            return BigDecimal.ZERO;
        switch (maMon.toUpperCase()) {
            case "TO":
                return nvl(d.getTO());
            case "LI":
                return nvl(d.getLI());
            case "HO":
                return nvl(d.getHO());
            case "SI":
                return nvl(d.getSI());
            case "SU":
                return nvl(d.getSU());
            case "DI":
                return nvl(d.getDI());
            case "VA":
                return nvl(d.getVA());
            case "N1_THI":
                return nvl(d.getN1_THI());
            case "N1_CC":
                return nvl(d.getN1_CC());
            case "CNCN":
                return nvl(d.getCNCN());
            case "CNNN":
                return nvl(d.getCNNN());
            case "TI":
                return nvl(d.getTI());
            case "KTPL":
                return nvl(d.getKTPL());
            case "NL1":
                return nvl(d.getNL1());
            case "NK1":
                return nvl(d.getNK1());
            case "NK2":
                return nvl(d.getNK2());
            default:
                System.err.println("[ScoreCalc] Không nhận diện được mã môn: " + maMon);
                return BigDecimal.ZERO;
        }
    }

    private BigDecimal nvl(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }

}
