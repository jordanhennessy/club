package com.jordan.club.competition.controller;

import com.jordan.club.competition.dto.request.UpdateCompetitionRequest;
import com.jordan.club.competition.dto.response.CompetitionResponse;
import com.jordan.club.competition.dto.request.CreateCompetitionRequest;
import com.jordan.club.competition.service.CompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<List<CompetitionResponse>> getAll() {
        return ResponseEntity.ok(competitionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CompetitionResponse> create(@Valid @RequestBody CreateCompetitionRequest newCompetition) {
        CompetitionResponse created = competitionService.save(newCompetition);
        return ResponseEntity.status(CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetitionResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateCompetitionRequest updatedCompetition
    ) {
        CompetitionResponse updated = competitionService.update(id, updatedCompetition);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        competitionService.delete(id);
        return ResponseEntity.ok("Competition deleted successfully");
    }

    @GetMapping("/join")
    public ResponseEntity<CompetitionResponse> joinCompetition(
            @RequestParam(name = "joinCode") String joinCode,
            @RequestParam(name = "userId") Long userId
    ) {
        CompetitionResponse competition = competitionService.joinCompetition(joinCode, userId);
        return ResponseEntity.ok(competition);
    }
}
