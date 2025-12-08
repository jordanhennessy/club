package com.jordan.club.gameweek.controller;

import com.jordan.club.gameweek.dto.GameWeekDTO;
import com.jordan.club.gameweek.service.GameWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gameweeks")
@RequiredArgsConstructor
public class GameWeekController {

    private final GameWeekService gameWeekService;

    @GetMapping
    public ResponseEntity<List<GameWeekDTO>>
    getAllGameWeeks(@RequestParam(name = "status", required = false) String status) {
        return ResponseEntity.ok(gameWeekService.getByStatus(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameWeekDTO> getGameWeekById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(gameWeekService.getById(id));
    }

}
