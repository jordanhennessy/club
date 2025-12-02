package com.jordan.club.match.entity;

import com.jordan.club.match.dto.MatchStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "fixtures")
@Data
@Builder
public class Fixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "away_team")
    private String awayTeam;

    @Column(name = "date")
    private Date matchDate;

    @Column(name = "game_week")
    private int gameWeek;

    @Column(name = "status")
    private MatchStatus status;

}
