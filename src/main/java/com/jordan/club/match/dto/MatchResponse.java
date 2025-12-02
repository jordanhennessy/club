package com.jordan.club.match.dto;

import lombok.Data;

import java.util.List;

@Data
public class MatchResponse {

    private List<Match> matches;

}
