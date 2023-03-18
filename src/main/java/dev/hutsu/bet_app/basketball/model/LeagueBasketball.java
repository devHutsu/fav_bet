package dev.hutsu.bet_app.basketball.model;

import dev.hutsu.bet_app.model.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(name = "uniq_key",
//columnNames = {"country_id", "title"}))
public class LeagueBasketball {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, unique = true)
    private String url;

    @OneToMany(mappedBy = "leagueBasketball")
    private List<EventBasketball> eventBasketballs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;



}
