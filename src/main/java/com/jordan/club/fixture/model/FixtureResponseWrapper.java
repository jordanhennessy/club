package com.jordan.club.fixture.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureResponseWrapper {

    @JsonProperty("matches")
    private List<FixtureResponse> fixtureResponses;

}
