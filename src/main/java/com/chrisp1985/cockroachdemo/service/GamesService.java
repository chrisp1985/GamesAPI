package com.chrisp1985.cockroachdemo.service;

import com.chrisp1985.cockroachdemo.configuration.GameMapper;
import com.chrisp1985.cockroachdemo.model.GameConsoleDto;
import com.chrisp1985.cockroachdemo.model.GameDto;
import com.chrisp1985.cockroachdemo.model.LowestRankedGameDto;
import com.chrisp1985.cockroachdemo.repository.GamesRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GamesService {

    private final GamesRepository gamesRepository;
    private final GameMapper gameMapper;

    public GamesService(GamesRepository gamesRepository, GameMapper gameMapper) {
        this.gamesRepository = gamesRepository;
        this.gameMapper = gameMapper;
    }

    @Cacheable(value = "gamesCache")
    public List<GameDto> findAllGames() {
        var dtoList = gameMapper.toDtoList(gamesRepository.findAll());
        return dtoList;
    }

    @Cacheable(value = "gamesCache")
    public List<GameConsoleDto> findAllGamesWithConsoles() {
        var dtoList = gamesRepository.findAllWithConsole();
        return dtoList;
    }

    public GameDto findGame(UUID gameId) {
        return gameMapper.toDto(gamesRepository.findById(gameId));
    }

    @CacheEvict(value = "gamesCache", allEntries = true)
    @Transactional
    public GameDto save(GameDto game) {
        return gameMapper.toDto(gamesRepository.save(gameMapper.toEntity(game)));
    }

    public void cancelQuery() {
        gamesRepository.cancelRunningQuery();
    }

    public void startLongQuery() {
        gamesRepository.startLongRunningQuery();
    }

    public List<LowestRankedGameDto> getLowestRankedGames(Integer rating, Integer count) {
        return gamesRepository.findListOfLowestRatings(rating, count);
    }
}
