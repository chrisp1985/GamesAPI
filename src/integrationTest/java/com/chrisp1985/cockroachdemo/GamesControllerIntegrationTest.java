package com.chrisp1985.cockroachdemo;

import com.chrisp1985.cockroachdemo.model.GameDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class GamesControllerIntegrationTest {

    @Container
    static CockroachContainer cockroachContainer = new CockroachContainer("cockroachdb/cockroach")
            .withCommand("start-single-node --insecure")
            .withExposedPorts(26257, 8080);

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", cockroachContainer::getJdbcUrl);
        registry.add("spring.datasource.username", cockroachContainer::getUsername);
        registry.add("spring.datasource.password", cockroachContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/v1/games";
    }

    @Test
    void testAddGame() {
        UUID gameId = UUID.randomUUID();
        GameDto newGame = new GameDto(gameId, "Halo", 1, 85);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GameDto> request = new HttpEntity<>(newGame, headers);

        ResponseEntity<GameDto> postResponse = restTemplate.postForEntity(baseUrl() + "/", request, GameDto.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getName()).isEqualTo("Halo");
    }

    @Test
    void testGetGames() {
        ResponseEntity<List<GameDto>> response = restTemplate.exchange(
                baseUrl() + "/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GameDto>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
