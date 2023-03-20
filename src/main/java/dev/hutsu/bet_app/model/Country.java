package dev.hutsu.bet_app.model;


import dev.hutsu.bet_app.volleyball.model.LeagueVolleyball;
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
@Entity
@Table
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String title;
//    @OneToMany(mappedBy = "country")
//    private List<LeagueBasketball> leagueBasketballs;

    @OneToMany(mappedBy = "country")
    private List<LeagueVolleyball> leagueVolleyballs;



    @Transient
    private List<League> leagueListTmp;

}
