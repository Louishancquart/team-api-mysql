package com.football.team.nice.controller;

import com.football.team.nice.exception.ResourceNotFoundException;
import com.football.team.nice.model.Player;
import com.football.team.nice.model.Team;
import com.football.team.nice.repository.PlayerRepository;
import com.football.team.nice.repository.TeamRepository;
import com.football.team.nice.util.SortColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Locale;


/**
 * Manage Team REST endpoint
 */
@CrossOrigin(origins = "http://localhost:4200") // Allows queries from Angular front
@RestController
@Validated
public class TeamController {

    Logger LOGGER = LoggerFactory.getLogger(TeamController.class);


    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    /**
     * Get the team list with pageable parameters
     *
     * @param size     number returned elements
     * @param filter column to filter the list with : default sort by team name
     * @param page  page number
     * @return paginated team list
     */
    @GetMapping("/teams")
    public ResponseEntity<Page> getAllTeams(@RequestParam(defaultValue = "50") int size,
                                            @RequestParam(defaultValue = "name") String filter,
                                            @RequestParam(defaultValue = "0") int page) {


        Sort sorting = Sort.by(Sort.Direction.ASC, SortColumn.valueOf(filter.toUpperCase(Locale.ROOT)).name().toLowerCase(Locale.ROOT));
        Page<Team> teams = teamRepository.findAll(PageRequest.of(Math.abs(page), size, sorting));

        if (teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        LOGGER.info(MessageFormat
                .format("GET: paginated team list with size: {0}, filter on {1}, page : {2} ",
                        size, filter, page));
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }


    /**
     * Get team by the id
     *
     * @param id of the team
     * @return the team designated by the id
     */
    @GetMapping("/team/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Team with id = " + id));

        LOGGER.info("GET team : "+ id);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }


    /**
     *  Add a team
     * @param team The team to add
     * @return
     */
    @PostMapping("/team")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team _team = teamRepository.save(new Team(team.getName(), team.getAcronym(), team.getBudget()));
        for (Player p : team.getPlayers()) {
            p.setTeam(_team);
            playerRepository.save(p);
            LOGGER.trace("POST: Player "+ p.getId() +" added to team : "+ _team.getId());
        }
        LOGGER.info("POST: Add team : "+ _team.getId());
        return new ResponseEntity<>(_team, HttpStatus.CREATED);
    }


    /**
     * Update a team (without the players)
     * @param id id of the team to update
     * @param team the new Team object
     * @return Team object
     */
    @PutMapping("/team/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") long id, @RequestBody Team team) {
        Team _team = teamRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("PUT: Could not retrieve team : "+ id);
                    return new ResourceNotFoundException("Not found Team with id = " + id);
                });

        _team.setName(team.getName());
        _team.setAcronym(team.getAcronym());
        _team.setBudget(team.getBudget());

        LOGGER.info("PUT: Update team : "+ id);

        return new ResponseEntity<>(teamRepository.save(_team), HttpStatus.OK);
    }


    /**
     * Delete a team
     * @param id id of the team to delete
     * @return Empty response
     */
    @DeleteMapping("/team/{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable("id") long id) {
        teamRepository.deleteById(id);

        LOGGER.info("DELETE: team : "+ id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Delete All teams
     * @return  Empty response
     */
    @DeleteMapping("/teams")
    public ResponseEntity<HttpStatus> deleteAllTeams() {
        teamRepository.deleteAll();
        LOGGER.info("DELETE: ALL teams : ");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
