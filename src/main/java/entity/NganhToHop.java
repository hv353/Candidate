package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_nganh_tohop")
public class NganhToHop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String manganh;

    private String matohop;

    private String th_mon1;
    private Integer hsmon1;

    private String th_mon2;
    private Integer hsmon2;

    private String th_mon3;
    private Integer hsmon3;

    @Column(unique = true)
    private String tb_keys;

    private Boolean N1;
    private Boolean TO;
    private Boolean LI;
    private Boolean HO;
    private Boolean SI;
    private Boolean VA;
    private Boolean SU;
    private Boolean DI;
    private Boolean TI;
    private Boolean KHAC;
    private Boolean KTPL;

    private BigDecimal dolech;

    public NganhToHop() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManganh() {
        return manganh;
    }

    public void setManganh(String manganh) {
        this.manganh = manganh;
    }

    public String getMatohop() {
        return matohop;
    }

    public void setMatohop(String matohop) {
        this.matohop = matohop;
    }

    public String getTh_mon1() {
        return th_mon1;
    }

    public void setTh_mon1(String th_mon1) {
        this.th_mon1 = th_mon1;
    }

    public Integer getHsmon1() {
        return hsmon1;
    }

    public void setHsmon1(Integer hsmon1) {
        this.hsmon1 = hsmon1;
    }

    public String getTh_mon2() {
        return th_mon2;
    }

    public void setTh_mon2(String th_mon2) {
        this.th_mon2 = th_mon2;
    }

    public Integer getHsmon2() {
        return hsmon2;
    }

    public void setHsmon2(Integer hsmon2) {
        this.hsmon2 = hsmon2;
    }

    public String getTh_mon3() {
        return th_mon3;
    }

    public void setTh_mon3(String th_mon3) {
        this.th_mon3 = th_mon3;
    }

    public Integer getHsmon3() {
        return hsmon3;
    }

    public void setHsmon3(Integer hsmon3) {
        this.hsmon3 = hsmon3;
    }

    public String getTb_keys() {
        return tb_keys;
    }

    public void setTb_keys(String tb_keys) {
        this.tb_keys = tb_keys;
    }

    public Boolean getN1() {
        return N1;
    }

    public void setN1(Boolean n1) {
        N1 = n1;
    }

    public Boolean getTO() {
        return TO;
    }

    public void setTO(Boolean tO) {
        TO = tO;
    }

    public Boolean getLI() {
        return LI;
    }

    public void setLI(Boolean lI) {
        LI = lI;
    }

    public Boolean getHO() {
        return HO;
    }

    public void setHO(Boolean hO) {
        HO = hO;
    }

    public Boolean getSI() {
        return SI;
    }

    public void setSI(Boolean sI) {
        SI = sI;
    }

    public Boolean getVA() {
        return VA;
    }

    public void setVA(Boolean vA) {
        VA = vA;
    }

    public Boolean getSU() {
        return SU;
    }

    public void setSU(Boolean sU) {
        SU = sU;
    }

    public Boolean getDI() {
        return DI;
    }

    public void setDI(Boolean dI) {
        DI = dI;
    }

    public Boolean getTI() {
        return TI;
    }

    public void setTI(Boolean tI) {
        TI = tI;
    }

    public Boolean getKHAC() {
        return KHAC;
    }

    public void setKHAC(Boolean kHAC) {
        KHAC = kHAC;
    }

    public Boolean getKTPL() {
        return KTPL;
    }

    public void setKTPL(Boolean kTPL) {
        KTPL = kTPL;
    }

    public BigDecimal getDolech() {
        return dolech;
    }

    public void setDolech(BigDecimal dolech) {
        this.dolech = dolech;
    }

}