package com.jordan.club.competition.controller;

import com.jordan.club.common.controller.CommonController;
import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.dto.JoinCompetitionRequest;
import com.jordan.club.competition.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CompetitionDTO> getById(@PathVariable("id") Long id) {
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
            @PathVariable("id") Long id,
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

    // Custom endpoint for joining a competition
    @PostMapping("/{id}/join")
    public ResponseEntity<CompetitionDTO> joinCompetition(
            @PathVariable("id") Long competitionId,
            @RequestBody JoinCompetitionRequest request,
            @RequestHeader("User-Id") Long userId  // Temporary - replace with security context
    ) {
        CompetitionDTO competition = competitionService.joinCompetition(
                competitionId,
                userId,
                request.getJoinCode()
        );
        return ResponseEntity.ok(competition);
    }
}
