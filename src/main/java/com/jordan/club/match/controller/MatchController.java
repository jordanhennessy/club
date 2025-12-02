package com.jordan.club.match.controller;

import com.jordan.club.match.dto.Match;
import com.jordan.club.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping()
    public ResponseEntity<List<Match>> getMatches(@RequestParam(name = "gameWeek", required = false) Integer gameWeek) {
        List<Match> matches = matchService.getMatchesByGameWeek(gameWeek);
        return ResponseEntity.ok(matches);
    }

}
