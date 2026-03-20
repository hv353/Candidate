package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_nguyenvongxettuyen")
public class NguyenVongXetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnv")
    private Integer idnv;

    @Column(name = "nn_cccd", nullable = false)
    private String nnCccd;

    @Column(name = "nv_manganh", nullable = false)
    private String nvManganh;

    @Column(name = "nv_tt", nullable = false)
    private Integer nvThuTu;

    @Column(name = "diem_thxt")
    private BigDecimal diemThxt;

    @Column(name = "diem_utqd")
    private BigDecimal diemUtqd;

    @Column(name = "diem_cong")
    private BigDecimal diemCong;

    @Column(name = "diem_xettuyen")
    private BigDecimal diemXetTuyen;

    @Column(name = "nv_ketqua")
    private String nvKetQua;

    @Column(name = "nv_keys", unique = true)
    private String nvKeys;

    @Column(name = "tt_phuongthuc")
    private String phuongThuc;

    @Column(name = "tt_thm")
    private String toHopMon;

    public NguyenVongXetTuyen() {
    }

    // getter setter
}