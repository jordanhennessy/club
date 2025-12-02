package com.jordan.club.match.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullTime {

    @SerializedName("home")
    private int homeScore;

    @SerializedName("away")
    private int awayScore;

}
