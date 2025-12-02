package com.jordan.club.match.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CompetitionResponse {

    @SerializedName("currentSeason")
    private CurrentSeason currentSeasonInfo;

}
