package com.chrisp1985.cockroachdemo;

import com.chrisp1985.cockroachdemo.configuration.GameMapper;
import com.chrisp1985.cockroachdemo.model.Game;
import com.chrisp1985.cockroachdemo.model.GameDto;
import com.chrisp1985.cockroachdemo.model.LowestRankedGameDto;
import com.chrisp1985.cockroachdemo.repository.GamesRepository;
import com.chrisp1985.cockroachdemo.service.GamesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GamesServiceTests {

    @InjectMocks
    private GamesService gamesService;

    @Mock
    private GamesRepository gamesRepository;

    @Mock
    private GameMapper gameMapper;

    @Test
    void findAllGames_shouldReturnMappedDtoList() {
        UUID gameId = UUID.randomUUID();
        var entities = List.of(new Game(gameId, "findAllGames_shouldReturnMappedDtoList_GAME", 1, 87));
        var dtos = List.of(new GameDto(gameId, "findAllGames_shouldReturnMappedDtoList_GAME", 1, 87));

        when(gamesRepository.findAll()).thenReturn(entities);
        when(gameMapper.toDtoList(entities)).thenReturn(dtos);

        List<GameDto> result = gamesService.findAllGames();

        assertThat(result).isEqualTo(dtos);
        verify(gamesRepository).findAll();
        verify(gameMapper).toDtoList(entities);
    }

    @Test
    void findGame_shouldReturnMappedDto() {
        UUID gameId = UUID.randomUUID();
        Game entity = new Game(gameId, "findGame_shouldReturnMappedDto_GAME", 1, 87);
        GameDto dto = new GameDto(gameId, "findGame_shouldReturnMappedDto_GAME", 1, 87);

        when(gamesRepository.findById(gameId)).thenReturn(entity);
        when(gameMapper.toDto(entity)).thenReturn(dto);

        var result = gamesService.findGame(gameId);

        assertThat(result).isEqualTo(dto);
        verify(gamesRepository).findById(gameId);
        verify(gameMapper).toDto(entity);
    }

    @Test
    void save_shouldConvertAndReturnSavedGameDto() {
        GameDto inputDto = new GameDto();
        Game entity = new Game();
        Game savedEntity = new Game();
        GameDto outputDto = new GameDto();

        when(gameMapper.toEntity(inputDto)).thenReturn(entity);
        when(gamesRepository.save(entity)).thenReturn(savedEntity);
        when(gameMapper.toDto(savedEntity)).thenReturn(outputDto);

        var result = gamesService.save(inputDto);

        assertThat(result).isEqualTo(outputDto);
        verify(gameMapper).toEntity(inputDto);
        verify(gamesRepository).save(entity);
        verify(gameMapper).toDto(savedEntity);
    }

    @Test
    void cancelQuery_shouldInvokeRepositoryMethod() {
        gamesService.cancelQuery();
        verify(gamesRepository).cancelRunningQuery();
    }

    @Test
    void startLongQuery_shouldInvokeRepositoryMethod() {
        gamesService.startLongQuery();
        verify(gamesRepository).startLongRunningQuery();
    }

    @Test
    void getLowestRankedGames_shouldReturnRepositoryResult() {
        int rating = 50;
        int count = 5;
        List<LowestRankedGameDto> expected = List.of(new LowestRankedGameDto());

        when(gamesRepository.findListOfLowestRatings(rating, count)).thenReturn(expected);

        var result = gamesService.getLowestRankedGames(rating, count);

        assertThat(result).isEqualTo(expected);
        verify(gamesRepository).findListOfLowestRatings(rating, count);
    }

}
