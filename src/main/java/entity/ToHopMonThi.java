package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "xt_tohop_monthi")
public class ToHopMonThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtohop")
    private Integer idtohop;

    @Column(name = "matohop", unique = true, nullable = false)
    private String maToHop;

    @Column(name = "mon1", nullable = false)
    private String mon1;

    @Column(name = "mon2", nullable = false)
    private String mon2;

    @Column(name = "mon3", nullable = false)
    private String mon3;

    @Column(name = "tentohop")
    private String tenToHop;

    public ToHopMonThi() {
    }

    // getter setter
}