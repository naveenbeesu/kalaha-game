package com.bol.kalahagame.controller;

import com.bol.kalahagame.model.Constants;
import com.bol.kalahagame.model.KalahaGame;
import com.bol.kalahagame.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KalahaGameControllerIntegrationTest {

    TestRestTemplate restTemplate = new TestRestTemplate();
    int[] player1Pits;
    int[] player2Pits;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        player1Pits = new int[Constants.NUMBER_OF_PITS];
        player2Pits = new int[Constants.NUMBER_OF_PITS];

        Arrays.fill(player1Pits, Constants.NUMBER_OF_STARTING_STONES);
        Arrays.fill(player2Pits, Constants.NUMBER_OF_STARTING_STONES);
    }

    @Test
    void testStartGameEndpoint() {
        int[] expectedPlayer1Pits = new int[]{6, 6, 6, 6, 6, 6};
        int[] expectedPlayer2Pits = new int[]{6, 6, 6, 6, 6, 6};

        //HttpEntity<> entity = new HttpEntity<>();

        ResponseEntity<KalahaGame> actualResponseEntity = restTemplate.exchange(
                buildUrl("/kalaha/startGame?id=e0250a8e-bf34-434f-8494-13872d5b1183"), HttpMethod.GET, null, KalahaGame.class);

        assertEquals(HttpStatusCode.valueOf(200), actualResponseEntity.getStatusCode());
        KalahaGame actualResponse = actualResponseEntity.getBody();

        assert actualResponse != null;

        assertEquals(Constants.NO_WINNER, actualResponse.winnerOfGame);
        assertEquals(Constants.PLAYER_ONE, actualResponse.presentPlayer);
        assertArrayEquals(expectedPlayer1Pits, actualResponse.player1.stonesInPits);
        assertArrayEquals(expectedPlayer2Pits, actualResponse.player2.stonesInPits);
        assertEquals(0, actualResponse.player1.stonesInBigPit);
        assertEquals(0, actualResponse.player2.stonesInBigPit);
    }

    @Test
    void testMoveStonesEndpointForSuccessScenario() {
        int[] expectedPlayer1Pits = new int[]{6, 6, 0, 7, 7, 7};
        int[] expectedPlayer2Pits = new int[]{7, 7, 6, 6, 6, 6};

        HttpEntity<Step> entity = new HttpEntity<>(new Step("c106c1ac-9f05-408c-a447-fd301c961c0a", 2, "player1"));

        KalahaGame actualResponse = restTemplate.exchange(
                buildUrl("/kalaha/moveStones"), HttpMethod.POST, entity, KalahaGame.class).getBody();

        assert actualResponse != null;

        assertEquals(Constants.NO_WINNER, actualResponse.winnerOfGame);
        assertEquals(Constants.PLAYER_TWO, actualResponse.presentPlayer);
        assertArrayEquals(expectedPlayer1Pits, actualResponse.player1.stonesInPits);
        assertArrayEquals(expectedPlayer2Pits, actualResponse.player2.stonesInPits);
        assertEquals(1, actualResponse.player1.stonesInBigPit);
        assertEquals(0, actualResponse.player2.stonesInBigPit);
    }

    private String buildUrl(String uri) {
        return "http://localhost:" + port + uri;
    }
}
