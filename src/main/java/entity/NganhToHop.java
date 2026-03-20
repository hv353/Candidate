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

    @Column(unique=true)
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

    public NganhToHop() {}
}