package com.rdiachuk.edu.calcio.service.impl;

import com.rdiachuk.edu.calcio.persistence.entity.Player;
import com.rdiachuk.edu.calcio.persistence.repository.PlayerRepository;
import com.rdiachuk.edu.calcio.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by roman.diachuk on 3/15/2017.
 */
@Service
@Transactional
public class JpaPlayerService implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player findById(Long id) {
        return playerRepository.findOne(id);
    }

    @Override
    public Player findByFirstNameAndLastName(String firstName, String lastName) {
        return playerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public void updatePlayer(Player player) {
        savePlayer(player);
    }

    @Override
    public void deletePlayerById(Long id) {
        playerRepository.delete(id);
    }

    @Override
    public void deleteAllPlayers() {
        playerRepository.deleteAll();
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public boolean isPlayerExist(Player playerToCheck) {
        Player player = findByFirstNameAndLastName(playerToCheck.getFirstName(), playerToCheck.getLastName());
        return Optional.ofNullable(player).isPresent();
    }
}
