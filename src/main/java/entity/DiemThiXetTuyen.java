package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_diemthixettuyen")
public class DiemThiXetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddiemthi;

    @Column(unique = true, nullable = false)
    private String cccd;

    private String sobaodanh;

    @Column(name = "d_phuongthuc")
    private String phuongthuc;

    private BigDecimal TO;
    private BigDecimal LI;
    private BigDecimal HO;
    private BigDecimal SI;
    private BigDecimal SU;
    private BigDecimal DI;
    private BigDecimal VA;

    private BigDecimal N1_THI;
    private BigDecimal N1_CC;

    private BigDecimal CNCN;
    private BigDecimal CNNN;
    private BigDecimal TI;
    private BigDecimal KTPL;

    private BigDecimal NL1;
    private BigDecimal NK1;
    private BigDecimal NK2;

    public DiemThiXetTuyen() {
    }

    public Integer getIddiemthi() {
        return iddiemthi;
    }

    public void setIddiemthi(Integer iddiemthi) {
        this.iddiemthi = iddiemthi;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSobaodanh() {
        return sobaodanh;
    }

    public void setSobaodanh(String sobaodanh) {
        this.sobaodanh = sobaodanh;
    }

    public String getPhuongthuc() {
        return phuongthuc;
    }

    public void setPhuongthuc(String phuongthuc) {
        this.phuongthuc = phuongthuc;
    }

    public BigDecimal getTO() {
        return TO;
    }

    public void setTO(BigDecimal tO) {
        TO = tO;
    }

    public BigDecimal getLI() {
        return LI;
    }

    public void setLI(BigDecimal lI) {
        LI = lI;
    }

    public BigDecimal getHO() {
        return HO;
    }

    public void setHO(BigDecimal hO) {
        HO = hO;
    }

    public BigDecimal getSI() {
        return SI;
    }

    public void setSI(BigDecimal sI) {
        SI = sI;
    }

    public BigDecimal getSU() {
        return SU;
    }

    public void setSU(BigDecimal sU) {
        SU = sU;
    }

    public BigDecimal getDI() {
        return DI;
    }

    public void setDI(BigDecimal dI) {
        DI = dI;
    }

    public BigDecimal getVA() {
        return VA;
    }

    public void setVA(BigDecimal vA) {
        VA = vA;
    }

    public BigDecimal getN1_THI() {
        return N1_THI;
    }

    public void setN1_THI(BigDecimal n1_THI) {
        N1_THI = n1_THI;
    }

    public BigDecimal getN1_CC() {
        return N1_CC;
    }

    public void setN1_CC(BigDecimal n1_CC) {
        N1_CC = n1_CC;
    }

    public BigDecimal getCNCN() {
        return CNCN;
    }

    public void setCNCN(BigDecimal cNCN) {
        CNCN = cNCN;
    }

    public BigDecimal getCNNN() {
        return CNNN;
    }

    public void setCNNN(BigDecimal cNNN) {
        CNNN = cNNN;
    }

    public BigDecimal getTI() {
        return TI;
    }

    public void setTI(BigDecimal tI) {
        TI = tI;
    }

    public BigDecimal getKTPL() {
        return KTPL;
    }

    public void setKTPL(BigDecimal kTPL) {
        KTPL = kTPL;
    }

    public BigDecimal getNL1() {
        return NL1;
    }

    public void setNL1(BigDecimal nL1) {
        NL1 = nL1;
    }

    public BigDecimal getNK1() {
        return NK1;
    }

    public void setNK1(BigDecimal nK1) {
        NK1 = nK1;
    }

    public BigDecimal getNK2() {
        return NK2;
    }

    public void setNK2(BigDecimal nK2) {
        NK2 = nK2;
    }

}