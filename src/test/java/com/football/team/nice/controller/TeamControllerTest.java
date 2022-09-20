package com.football.team.nice.controller;

import com.football.team.nice.model.Team;
import com.football.team.nice.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * API class test
 * Many other test might be added for each endpoint
 * This is general test approach for create Team
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private  TeamRepository teamRepository;


    @Test
    public void postTeam() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", "");


        URL url = new URL("http://localhost:" + port + "/team");
        Team testTeam = new Team("Paris Saint Germain","PSG", new BigDecimal(12000));

        HttpEntity<Team> requestEntity = new HttpEntity<>(testTeam, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Team> responseEntity = restTemplate.postForEntity(url.toString(), requestEntity, Team.class);


        String requestOutput  = Objects.requireNonNull(responseEntity.getBody()).toString();
        assertTrue(requestOutput.contains("name='Paris Saint Germain'"));
        assertTrue(requestOutput.contains("acronym='PSG'"));
        assertTrue(requestOutput.contains("budget=12000"));
    }



}