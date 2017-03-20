package com.rdiachuk.edu.calcio.controller;

import com.rdiachuk.edu.calcio.persistence.entity.Player;
import com.rdiachuk.edu.calcio.service.PlayerService;
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
 * Created by roman.diachuk on 3/15/2017.
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;

    @RequestMapping("/")
    public ResponseEntity<?> listAllPlayers() {
        logger.info("Fetching all Players");
        try {
            List<Player> players = playerService.findAllPlayers();
            if (players.isEmpty()) {
                // TODO: consider to return empty list of players.
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            logger.info("Returned {} Players", players.size());
            return new ResponseEntity<>(players, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.RETRIEVE_FAILURE.toString(), CalcioEntity.PLAYER, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable("id") long id) {
        logger.info("Fetching Player with id {}", id);
        try {
            Player player = playerService.findById(id);
            if (player == null) {
                String errorMsg = CalcioError.PLAYER_NOT_FOUND.toString();
                logger.error(errorMsg);
                return new ResponseEntity(errorMsg, HttpStatus.NOT_FOUND);
            }
            logger.info("Returning {}", player);
            return new ResponseEntity(player, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.RETRIEVE_FAILURE.toString(), CalcioEntity.PLAYER, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createTeam(@RequestBody Player player, UriComponentsBuilder ucBuilder) {
        logger.info("Creating {}", player);
        try {
            if (playerService.isPlayerExist(player)) {
                String errorMsg = String.format(CalcioError.CREATE_FAILURE.toString(), CalcioError.DUPLICATE_PLAYER);
                logger.error(errorMsg);
                return new ResponseEntity(new CalcioErrorResponseType(errorMsg), HttpStatus.CONFLICT);
            }
            playerService.savePlayer(player);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/player/{id}").buildAndExpand(player.getId()).toUri());
            logger.info("Created successfully!");
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.CREATE_FAILURE.toString(), CalcioEntity.PLAYER, e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePlayer(@PathVariable("id") long id, @RequestBody Player player) {
        logger.info("Updating Player with id {}", id);
        try {
            Optional<Player> currentPlayerOpt = Optional.ofNullable(playerService.findById(id));
            if (!currentPlayerOpt.isPresent()) {
                String errorMsg = String.format(CalcioError.UPDATE_FAILURE.toString(), CalcioError.PLAYER_NOT_FOUND);
                logger.error(errorMsg);
                return new ResponseEntity(new CalcioErrorResponseType(errorMsg), HttpStatus.NOT_FOUND);
            }
            Player currentPlayer = currentPlayerOpt.get();
            currentPlayer.setFirstName(player.getFirstName());
            currentPlayer.setLastName(player.getLastName());
            playerService.updatePlayer(currentPlayer);
            logger.info("Updated successfully!");
            return new ResponseEntity(currentPlayer, HttpStatus.OK);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.UPDATE_FAILURE.toString(), e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePlayer(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Player with id {}", id);
        try {
            Optional<Player> playerOpt = Optional.ofNullable(playerService.findById(id));
            if (!playerOpt.isPresent()) {
                String errorMsg = String.format(CalcioError.DELETE_FAILURE.toString(), CalcioError.PLAYER_NOT_FOUND);
                logger.error(errorMsg);
                return new ResponseEntity(errorMsg, HttpStatus.NOT_FOUND);
            }
            playerService.deletePlayerById(id);
            logger.info("Deleted successfully!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.DELETE_FAILURE.toString(), e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllPlayers() {
        logger.info("Deleting all Players.");
        try {
            playerService.deleteAllPlayers();
            logger.info("Deleted successfully!");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String errorMsg = String.format(CalcioError.DELETE_FAILURE.toString(), e.getMessage());
            logger.error(errorMsg, e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
