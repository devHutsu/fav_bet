package dev.hutsu.bet_app.basketball.model;

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
//@Entity
//@Table
public class EventBasketball {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String firstTeam;
    @Column(nullable = false)
    private String secondTeam;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(nullable = false)
    private LocalDateTime dateEvent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "league_basketball_id", referencedColumnName = "id")
    private LeagueBasketball leagueBasketball;

    private Float coeffFirstTeam;
    private Float coeffSecondTeam;

    private Float coeffFirstQuarter_p1;
    private Float coeffSecondQuarter_p2;
}
