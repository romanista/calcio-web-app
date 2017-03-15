package com.rdiachuk.edu.calcio.controller;

import com.rdiachuk.edu.calcio.persistence.entity.Team;
import com.rdiachuk.edu.calcio.service.TeamService;
import com.rdiachuk.edu.calcio.util.CalcioEntity;
import com.rdiachuk.edu.calcio.util.CalcioError;
import com.rdiachuk.edu.calcio.util.CalcioErrorResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * Created by roman.diachuk on 2/28/2017.
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    public static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @RequestMapping("/")
    public ResponseEntity<List<Team>> listAllTeams() {
        logger.info("Fetching all Teams");
        try {
            List<Team> teams = teamService.findAllTeams();
            if (teams.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            logger.info("Returned {} Teams", teams.size());
            return new ResponseEntity(teams, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.RETRIEVE_FAILURE.toString(), CalcioEntity.TEAM, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getTeam(@PathVariable("id") long id) {
        logger.info("Fetching Team with id {}", id);
        try {
            Team team = teamService.findById(id);
            if (team == null) {
                String errorMsg = CalcioError.TEAM_NOT_FOUND.toString();
                logger.error(errorMsg);
                return new ResponseEntity(errorMsg, HttpStatus.NOT_FOUND);
            }
            logger.info("Returning {}", team);
            return new ResponseEntity(team, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.RETRIEVE_FAILURE.toString(), CalcioEntity.TEAM, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createTeam(@RequestBody Team team, UriComponentsBuilder ucBuilder) {
        logger.info("Creating {}", team);
        try {
            if (teamService.isTeamExist(team)) {
                String errorMsg = String.format(CalcioError.CREATE_FAILURE.toString(), CalcioError.DUPLICATE_TEAM);
                logger.error(errorMsg);
                return new ResponseEntity(new CalcioErrorResponseType(errorMsg), HttpStatus.CONFLICT);
            }
            teamService.saveTeam(team);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/team/{id}").buildAndExpand(team.getId()).toUri());
            logger.info("Created successfully!");
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.CREATE_FAILURE.toString(), CalcioEntity.TEAM, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTeam(@PathVariable("id") long id, @RequestBody Team team) {
        logger.info("Updating Team with id {}", id);
        try {
            Optional<Team> currentTeamOpt = Optional.ofNullable(teamService.findById(id));
            if (!currentTeamOpt.isPresent()) {
                String errorMsg = String.format(CalcioError.UPDATE_FAILURE.toString(), CalcioError.TEAM_NOT_FOUND);
                logger.error(errorMsg);
                return new ResponseEntity(new CalcioErrorResponseType(errorMsg), HttpStatus.NOT_FOUND);
            }
            Team currentTeam = currentTeamOpt.get();
            currentTeam.setName(team.getName());
            teamService.updateTeam(currentTeam);
            logger.info("Updated successfully!");
            return new ResponseEntity(currentTeam, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.UPDATE_FAILURE.toString(), e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeam(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Team with id {}", id);
        try {
            Team team = teamService.findById(id);
            if (team == null) {
                String errorMsg = String.format(CalcioError.DELETE_FAILURE.toString(), CalcioEntity.TEAM, CalcioError.TEAM_NOT_FOUND);
                logger.error(errorMsg);
                return new ResponseEntity(errorMsg, HttpStatus.NOT_FOUND);
            }
            teamService.deleteTeamById(id);
            logger.info("Deleted successfully!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.DELETE_FAILURE.toString(), CalcioEntity.TEAM, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllTeams() {
        logger.info("Deleting all Teams.");
        try {
            teamService.deleteAllTeams();
            logger.info("Deleted successfully!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.DELETE_FAILURE.toString(), CalcioEntity.TEAM, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
