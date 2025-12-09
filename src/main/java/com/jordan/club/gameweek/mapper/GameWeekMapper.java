package com.jordan.club.gameweek.mapper;

import com.jordan.club.common.mapper.CommonMapper;
import com.jordan.club.gameweek.dto.GameWeekDTO;
import com.jordan.club.gameweek.entity.GameWeek;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameWeekMapper implements CommonMapper<GameWeek, GameWeekDTO> {
    @Override
    public GameWeek fromDTO(GameWeekDTO dto) {
        return GameWeek.builder()
                .id(dto.getId())
                .gameWeek(dto.getGameWeek())
                .deadline(dto.getDeadline())
                .status(dto.getStatus())
                .build();
    }

    @Override
    public GameWeekDTO toDTO(GameWeek entity) {
        return GameWeekDTO.builder()
                .id(entity.getId())
                .gameWeek(entity.getGameWeek())
                .deadline(entity.getDeadline())
                .status(entity.getStatus())
                .build();
    }
}
