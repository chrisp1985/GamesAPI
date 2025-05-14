package com.chrisp1985.cockroachdemo.controller;

import com.chrisp1985.cockroachdemo.model.GameConsoleDto;
import com.chrisp1985.cockroachdemo.model.GameDto;
import com.chrisp1985.cockroachdemo.model.LowestRankedGameDto;
import com.chrisp1985.cockroachdemo.service.GamesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/games")
@Slf4j
public class GamesController {

    GamesService gamesService;

    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<GameDto>> getGames() {
        log.info("Getting all games.");
        return ResponseEntity.ok(
                this.gamesService.findAllGames());
    }

    @GetMapping(value = "/console")
    public ResponseEntity<List<GameConsoleDto>> getGamesWithConsole() {
        log.info("Getting all games with mapped console.");
        return ResponseEntity.ok(
                this.gamesService.findAllGamesWithConsoles());
    }

    @GetMapping(value = "/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable UUID gameId) {
        log.info("Getting gameId {}.", gameId);
        return ResponseEntity.ok(
                this.gamesService.findGame(gameId));
    }

    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameDto> addGame(@RequestBody @Valid GameDto game) {
        log.info("Adding Game to DB.");
        return ResponseEntity.ok(
                this.gamesService.save(game));
    }

    @GetMapping(value = "/hang")
    public void hangDatabase() {
        log.info("Hang request received.");
        this.gamesService.startLongQuery();
    }

    @PutMapping(value = "/hang")
    public void cancelHangDatabase() {
        log.info("Hang Cancellation request received.");
        this.gamesService.cancelQuery();
    }

    @GetMapping(value = "/lowestRanked")
    public ResponseEntity<List<LowestRankedGameDto>> getLowestRankedGames(@RequestParam Integer rating,
                                                                          @RequestParam Integer count) {
        log.info("Returning lowest ranked games.");
        return ResponseEntity.ok(this.gamesService.getLowestRankedGames(rating, count));
    }

}
