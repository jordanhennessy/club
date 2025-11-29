package com.jordan.club.competition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jordan.club.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompetitionDTO {
    private Long id;
    private String name;
    private String description;
    private Long organizerId;
    private List<Long> participantIds;
}
