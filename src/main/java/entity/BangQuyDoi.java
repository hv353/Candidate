package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_bangquydoi")
public class BangQuyDoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idqd")
    private Integer idqd;

    @Column(name = "d_phuongthuc")
    private String dPhuongThuc;

    @Column(name = "d_tohop")
    private String dToHop;

    @Column(name = "d_mon")
    private String dMon;

    @Column(name = "d_diema")
    private BigDecimal dDiemA;

    @Column(name = "d_diemb")
    private BigDecimal dDiemB;

    @Column(name = "d_diemc")
    private BigDecimal dDiemC;

    @Column(name = "d_diemd")
    private BigDecimal dDiemD;

    @Column(name = "d_maquydoi", unique = true)
    private String dMaQuyDoi;

    @Column(name = "d_phanvi")
    private String dPhanVi;

    public BangQuyDoi() {
    }

    public BangQuyDoi(String dPhuongThuc, String dToHop, String dMon,
                      BigDecimal dDiemA, BigDecimal dDiemB,
                      BigDecimal dDiemC, BigDecimal dDiemD,
                      String dMaQuyDoi, String dPhanVi) {

        this.dPhuongThuc = dPhuongThuc;
        this.dToHop = dToHop;
        this.dMon = dMon;
        this.dDiemA = dDiemA;
        this.dDiemB = dDiemB;
        this.dDiemC = dDiemC;
        this.dDiemD = dDiemD;
        this.dMaQuyDoi = dMaQuyDoi;
        this.dPhanVi = dPhanVi;
    }

    public Integer getIdqd() {
        return idqd;
    }

    public void setIdqd(Integer idqd) {
        this.idqd = idqd;
    }

    public String getdPhuongThuc() {
        return dPhuongThuc;
    }

    public void setdPhuongThuc(String dPhuongThuc) {
        this.dPhuongThuc = dPhuongThuc;
    }

    public String getdToHop() {
        return dToHop;
    }

    public void setdToHop(String dToHop) {
        this.dToHop = dToHop;
    }

    public String getdMon() {
        return dMon;
    }

    public void setdMon(String dMon) {
        this.dMon = dMon;
    }

    public BigDecimal getdDiemA() {
        return dDiemA;
    }

    public void setdDiemA(BigDecimal dDiemA) {
        this.dDiemA = dDiemA;
    }

    public BigDecimal getdDiemB() {
        return dDiemB;
    }

    public void setdDiemB(BigDecimal dDiemB) {
        this.dDiemB = dDiemB;
    }

    public BigDecimal getdDiemC() {
        return dDiemC;
    }

    public void setdDiemC(BigDecimal dDiemC) {
        this.dDiemC = dDiemC;
    }

    public BigDecimal getdDiemD() {
        return dDiemD;
    }

    public void setdDiemD(BigDecimal dDiemD) {
        this.dDiemD = dDiemD;
    }

    public String getdMaQuyDoi() {
        return dMaQuyDoi;
    }

    public void setdMaQuyDoi(String dMaQuyDoi) {
        this.dMaQuyDoi = dMaQuyDoi;
    }

    public String getdPhanVi() {
        return dPhanVi;
    }

    public void setdPhanVi(String dPhanVi) {
        this.dPhanVi = dPhanVi;
    }
}