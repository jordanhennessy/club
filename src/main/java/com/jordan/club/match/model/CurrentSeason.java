package com.jordan.club.match.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CurrentSeason {

    @SerializedName("currentMatchday")
    private int currentGameWeek;

}
