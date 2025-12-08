package com.jordan.club.gameweek.entity;

import com.jordan.club.gameweek.enums.GameWeekStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@Data
@Table(name = "game_weeks")
@AllArgsConstructor
@NoArgsConstructor
public class GameWeek {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private Integer gameWeek;

    @Column
    private GameWeekStatus status;

}
