package com.jordan.club.fixture.controller;

import com.jordan.club.fixture.dto.FixtureDTO;
import com.jordan.club.fixture.model.FixtureResponse;
import com.jordan.club.fixture.service.FixtureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fixtures")
@RequiredArgsConstructor
public class FixtureController {

    private final FixtureService fixtureService;

    @GetMapping()
    public ResponseEntity<List<FixtureDTO>> getFixtures(
            @RequestParam(name = "gameWeek", required = false) Integer gameWeek,
            @RequestParam(name = "status", required = false) String status
    ) {
        List<FixtureDTO> fixtureResponses = fixtureService.getFixturesByGameWeekAndStatus(gameWeek, status);
        return ResponseEntity.ok(fixtureResponses);
    }

    @GetMapping("/current")
    public ResponseEntity<List<FixtureDTO>> getCurrentFixtures() {
        List<FixtureDTO> fixtureResponses = fixtureService.getFixturesForCurrentGameWeek();
        return ResponseEntity.ok(fixtureResponses);
    }

}
