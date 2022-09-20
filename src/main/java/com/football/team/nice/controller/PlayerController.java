package com.football.team.nice.controller;

import com.football.team.nice.exception.ResourceNotFoundException;
import com.football.team.nice.model.Player;
import com.football.team.nice.model.Team;
import com.football.team.nice.repository.PlayerRepository;
import com.football.team.nice.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Manage Player related REST API actions
 */
@CrossOrigin(origins = "http://localhost:4200") // for angular
@RestController
@Validated
public class PlayerController {
    Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);


    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Get players for a team
     * @param teamId id of the team
     * @return player list
     */
    @GetMapping("/teams/{teamId}/players")
    public ResponseEntity<List<Player>> getAllPlayersByTeamId(@PathVariable(value = "teamId") Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("Not found Team with id = " + teamId);
        }

        List<Player> players = playerRepository.findByTeamId(teamId);
        LOGGER.info("GET players from team : "+ teamId);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    /**
     * Get a Player
     * @param id to get
     * @return Player object
     */
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayersByTeamId(@PathVariable(value = "id") Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Player with id = " + id));
        LOGGER.info("GET player : "+ id);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    /**
     * Add a player to a team
     * @param teamId id of the team
     * @param playerRequest Player object
     * @return Player object
     */
    @PostMapping("/teams/{teamId}/players")
    public ResponseEntity<Player> createPlayer(@PathVariable(value = "teamId") Long teamId,
                                               @RequestBody Player playerRequest) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Team with id = " + teamId));
        playerRequest.setTeam(team);
        Player player = playerRepository.save(playerRequest);
        LOGGER.info("POST player : "+ player.getId() + " to team " + teamId);

        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }

    /**
     * Update player
     * @param id id of player
     * @param playerRequest player object
     * @return Player object
     */
    @PutMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id, @RequestBody Player playerRequest) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlayerId " + id + "not found"));

        player.setName(playerRequest.getName());
        player.setPosition(playerRequest.getPosition());

        LOGGER.info("PUT player : "+ player.getId() );
        return new ResponseEntity<>(playerRepository.save(player), HttpStatus.OK);
    }


    /**
     * Delete a player
     * @param id of the player to delete
     * @return empty response
     */
    @DeleteMapping("/players/{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("id") long id) {
        playerRepository.deleteById(id);

        LOGGER.info("DELETE player : "+ id );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deleete all players from a team
     * @param teamId id of the team
     * @return empty response
     */
    @DeleteMapping("/teams/{teamId}/players")
    public ResponseEntity<List<Player>> deleteAllPlayersOfTeam(@PathVariable(value = "teamId") Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("Not found Team with id = " + teamId);
        }

        playerRepository.deleteByTeamId(teamId);
        LOGGER.info("DELETE all players from team " + teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
