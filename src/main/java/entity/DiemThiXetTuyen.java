package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_diemthixettuyen")
public class DiemThiXetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddiemthi;

    @Column(unique=true, nullable=false)
    private String cccd;

    private String sobaodanh;

    @Column(name="d_phuongthuc")
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

    public DiemThiXetTuyen() {}
}