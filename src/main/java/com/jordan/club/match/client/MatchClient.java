package com.jordan.club.match.client;

import com.google.gson.Gson;
import com.jordan.club.match.model.CompetitionResponse;
import com.jordan.club.match.model.Match;
import com.jordan.club.match.model.MatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import java.net.http.HttpResponse.BodyHandlers;

@Component
@RequiredArgsConstructor
public class MatchClient {

    @Value("${fixture.data.base.url:https://api.football-data.org/v4}")
    private String baseUrl;

    @Value("${fixture.data.season.year:2025}")
    private String seasonYear;

    @Value("${fixture.data.api.key}")
    private String apiKey;

    private static final String AUTH_HEADER = "X-Auth-Token";

    private final HttpClient httpClient;
    private final Gson gson;

    public List<Match> getAllMatches() throws IOException, InterruptedException {
        String getUrl = String.format("%s/competitions/PL/matches?season=%s", baseUrl, seasonYear);
        HttpRequest request = buildGetRequest(getUrl);
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        MatchResponse matchResponse = gson.fromJson(response.body(), MatchResponse.class);
        return matchResponse.getMatches();
    }

    public int getCurrentGameWeek() throws IOException, InterruptedException {
        String getUrl = String.format("%s/competitions/PL", baseUrl);
        HttpRequest request = buildGetRequest(getUrl);
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        CompetitionResponse competitionResponse = gson.fromJson(response.body(), CompetitionResponse.class);
        return competitionResponse.getCurrentSeasonInfo().getCurrentGameWeek();
    }

    private HttpRequest buildGetRequest(String url) {
        return HttpRequest.newBuilder()
                .GET()
                .header(AUTH_HEADER, apiKey)
                .uri(URI.create(url))
                .build();
    }

}
