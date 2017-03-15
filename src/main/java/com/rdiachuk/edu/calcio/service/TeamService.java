package com.rdiachuk.edu.calcio.service;

import com.rdiachuk.edu.calcio.persistence.entity.Team;

import java.util.List;

/**
 * Created by roman.diachuk on 2/28/2017.
 */
public interface TeamService {

    Team findById(Long id);

    Team findByName(String name);

    void saveTeam(Team team);

    void updateTeam(Team team);

    void deleteTeamById(Long id);

    void deleteAllTeams();

    List<Team> findAllTeams();

    boolean isTeamExist(Team team);
}
