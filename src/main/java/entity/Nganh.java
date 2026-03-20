package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_nganh")
public class Nganh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idnganh;

    @Column(nullable=false)
    private String manganh;

    @Column(nullable=false)
    private String tennganh;

    private String n_tohopgoc;

    private Integer n_chitieu;

    private BigDecimal n_diemsan;

    private BigDecimal n_diemtrungtuyen;

    private String n_tuyenthang;

    private String n_dgnl;

    private String n_thpt;

    private String n_vsat;

    private Integer sl_xtt;

    private Integer sl_dgnl;

    private Integer sl_vsat;

    private String sl_thpt;

    public Nganh() {}
}