package com.jordan.club.competition.controller;

import com.jordan.club.common.controller.CommonController;
import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.dto.JoinCompetitionRequest;
import com.jordan.club.competition.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/competitions")
@RequiredArgsConstructor
public class CompetitionController implements CommonController<CompetitionDTO> {

    private final CompetitionService competitionService;

    @Override
    @GetMapping
    public ResponseEntity<List<CompetitionDTO>> getAll() {
        return ResponseEntity.ok(competitionService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CompetitionDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<CompetitionDTO> create(@RequestBody CompetitionDTO competition) {
        CompetitionDTO created = competitionService.save(competition);
        return ResponseEntity.status(CREATED).body(created);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CompetitionDTO> update(
            @PathVariable Long id,
            @RequestBody CompetitionDTO updatedCompetition
    ) {
        CompetitionDTO updated = competitionService.update(id, updatedCompetition);
        return ResponseEntity.ok(updated);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        competitionService.delete(id);
        return ResponseEntity.ok("Competition deleted successfully");
    }

    @GetMapping("/join")
    public ResponseEntity<CompetitionDTO> joinCompetition(
            @RequestParam(name = "joinCode") String joinCode,
            @RequestParam(name = "userId") Long userId
    ) {
        CompetitionDTO competition = competitionService.joinCompetition(joinCode, userId);
        return ResponseEntity.ok(competition);
    }
}
