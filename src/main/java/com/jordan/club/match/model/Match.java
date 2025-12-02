package com.jordan.club.match.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class Match {

    private Team homeTeam;
    private Team awayTeam;
    private MatchStatus status;
    private int matchday;
    private Score score;

    @SerializedName("utcDate")
    private Date date;

}
