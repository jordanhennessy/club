package com.jordan.club.competition.controller;

import com.jordan.club.common.controller.CommonController;
import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/competitions")
public class CompetitionController implements CommonController<CompetitionDTO> {

    private final CompetitionService competitionService;

    @Override
    @GetMapping
    public ResponseEntity<List<CompetitionDTO>> getAll() {
        return ResponseEntity.ok().body(competitionService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CompetitionDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(competitionService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<CompetitionDTO> create(@RequestBody CompetitionDTO newCompetition) {
        return ResponseEntity.status(CREATED).body(competitionService.save(newCompetition));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CompetitionDTO> update(@PathVariable("id") Long id,
                                                 @RequestBody CompetitionDTO updatedCompetition) {
        return ResponseEntity.ok().body(competitionService.update(id, updatedCompetition));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(Long id) {
        competitionService.delete(id);
        return ResponseEntity.ok().body(String.format("Successfully deleted competition with id=%s", id));
    }
}
