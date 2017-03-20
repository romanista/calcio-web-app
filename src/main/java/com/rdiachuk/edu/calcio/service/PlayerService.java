package com.rdiachuk.edu.calcio.service;

import com.rdiachuk.edu.calcio.persistence.entity.Player;

import java.util.List;

/**
 * Created by roman.diachuk on 3/15/2017.
 */
public interface PlayerService {

    Player findById(Long id);

    Player findByFirstNameAndLastName(String firstName, String lastName);

    void savePlayer(Player player);

    void updatePlayer(Player player);

    void deletePlayerById(Long id);

    void deleteAllPlayers();

    List<Player> findAllPlayers();

    boolean isPlayerExist(Player player);
}
