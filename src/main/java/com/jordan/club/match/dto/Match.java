package com.jordan.club.match.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Match {

    private Team homeTeam;
    private Team awayTeam;
    private MatchStatus status;
    private int matchday;

    @SerializedName("utcDate")
    private Date date;

}
