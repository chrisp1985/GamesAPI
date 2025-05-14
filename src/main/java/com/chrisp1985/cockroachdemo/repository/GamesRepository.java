package com.chrisp1985.cockroachdemo.repository;

import com.chrisp1985.cockroachdemo.model.Game;
import com.chrisp1985.cockroachdemo.model.GameConsoleDto;
import com.chrisp1985.cockroachdemo.model.LowestRankedGameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Repository
@Slf4j
public class GamesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile Statement runningStatement;
    private volatile Future<?> queryFuture;

    public GamesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Game> gameRowMapper = (rs, rowNum) -> new Game(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getInt("console_id"),
            rs.getInt("rating")
    );

    public List<Game> findAll() {
        log.info("Getting Games from the Database");
        String sql = "SELECT id, name, console_id, rating FROM games";
        return jdbcTemplate.query(sql, gameRowMapper);
    }

    public List<GameConsoleDto> findAllWithConsole() {
        log.info("Getting Games with Console Names from the Database");
        String sql = "SELECT games.id, games.name, consoles.name as console_name, games.rating FROM games " +
                "JOIN consoles ON consoles.id = games.console_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new GameConsoleDto(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("console_name"),
                rs.getInt("rating")
        ));
    }

    public Game findById(UUID uuid) {
        return jdbcTemplate.queryForObject("SELECT id, name, console_id, rating FROM games WHERE id=?",
                BeanPropertyRowMapper.newInstance(Game.class), uuid);
    }

    public List<LowestRankedGameDto> findListOfLowestRatings(Integer rating, Integer count) {
        return jdbcTemplate.query(
                "select games.name as game_name, consoles.name as console_name from games " +
                        "join consoles on consoles.id = games.console_id " +
                        "where games.rating < ? limit ?",
                (rs, rowNum) -> new LowestRankedGameDto(
                        rs.getString("game_name"),
                        rs.getString("console_name")
                ), rating, count);
    }

    public Game save(Game game) {
        String sql = "INSERT INTO games (name, console_id, rating) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                game.getName(),
                game.getConsole_id(),
                game.getRating()
        );

        return game;
    }

    public synchronized String startLongRunningQuery() {
        if (queryFuture != null && !queryFuture.isDone()) {
            return "A query is already running.";
        }

        queryFuture = executorService.submit(() -> {
            try {
                jdbcTemplate.execute((Connection connection) -> {
                    try (Statement stmt = connection.createStatement()) {
                        runningStatement = stmt;
                        System.out.println("Starting long-running query...");
                        stmt.execute("SELECT pg_sleep(1) FROM generate_series(1, 1000000000)");
                        System.out.println("Query completed normally.");
                    } finally {
                        runningStatement = null;
                    }
                    return null;
                });
            } catch (DataAccessException ex) {
                System.out.println("Query cancelled or failed: " + ex.getMessage());
            }
        });

        return "Long-running query started.";
    }

    public synchronized String cancelRunningQuery() {
        if (runningStatement == null) {
            return "No running query to cancel.";
        }

        try {
            System.out.println("Attempting to cancel the running query...");
            runningStatement.cancel();
            return "Query cancellation requested.";
        } catch (Exception e) {
            return "Failed to cancel query: " + e.getMessage();
        } finally {
            runningStatement = null;
        }
    }

}
