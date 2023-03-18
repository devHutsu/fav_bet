package dev.hutsu.bet_app.volleyball.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class EventVolleyball {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstTeam;
    @Column(nullable = false)
    private String secondTeam;

    @Column(nullable = false)
    private LocalDateTime dataEvent;

    @Column(nullable = false, unique = true)
    private String url;

    private Float coeff_P1;
    private Float coeff_P2;

    private Float coeff_1_set_45_5_larger;
    private Float coeff_1_set_45_5_less;

    private Float coeff_2_set_45_5_larger;
    private Float coeff_2_set_45_5_less;

    private Float coeff_3_set_45_5_larger;
    private Float coeff_3_set_45_5_less;

    private Float coeff_4_set_45_5_larger;
    private Float coeff_4_set_45_5_less;

    private String res_1_set;
    private String res_2_set;
    private String res_3_set;
    private String res_4_set;



    @Column(unique = true, nullable = false)
    private Integer dataId;

    private String urlLive;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "league_voll_id", referencedColumnName = "id")
    private LeagueVolleyball leagueVolleyball;


}
