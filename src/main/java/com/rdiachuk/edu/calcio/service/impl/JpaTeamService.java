package com.rdiachuk.edu.calcio.service.impl;

import com.rdiachuk.edu.calcio.persistence.entity.Team;
import com.rdiachuk.edu.calcio.persistence.repository.TeamRepository;
import com.rdiachuk.edu.calcio.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by roman.diachuk on 2/20/2017.
 */
@Service
@Transactional
public class JpaTeamService implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team findById(Long id) {
        return teamRepository.findOne(id);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void updateTeam(Team team) {
        saveTeam(team);
    }

    @Override
    public void deleteTeamById(Long id) {
        teamRepository.delete(id);
    }

    @Override
    public void deleteAllTeams() {
        teamRepository.deleteAll();
    }

    @Override
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public boolean isTeamExist(Team team) {
        return Optional.ofNullable(findByName(team.getName())).isPresent();
    }
}
