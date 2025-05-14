package com.chrisp1985.cockroachdemo.configuration;

import com.chrisp1985.cockroachdemo.model.Game;
import com.chrisp1985.cockroachdemo.model.GameConsoleDto;
import com.chrisp1985.cockroachdemo.model.GameDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDto toDto(Game game);

    Game toEntity(GameDto gameDto);

    List<GameDto> toDtoList(List<Game> games);

    List<GameConsoleDto> toConsoleDtoList(List<Game> games);
}
