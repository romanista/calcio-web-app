package com.rdiachuk.edu.calcio.persistence.repository;

import com.rdiachuk.edu.calcio.persistence.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by roman.diachuk on 2/20/2017.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);
}
