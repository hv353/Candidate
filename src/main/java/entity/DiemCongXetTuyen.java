package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_diemcongxetuyen")
public class DiemCongXetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddiemcong;

    @Column(name="ts_cccd", nullable=false)
    private String tsCccd;

    private String manganh;

    private String matohop;

    private String phuongthuc;

    private BigDecimal diemCC;

    private BigDecimal diemUtxt;

    private BigDecimal diemTong;

    @Column(columnDefinition = "TEXT")
    private String ghichu;

    @Column(name="dc_keys", unique=true, nullable=false)
    private String dcKeys;

    public DiemCongXetTuyen() {}
}