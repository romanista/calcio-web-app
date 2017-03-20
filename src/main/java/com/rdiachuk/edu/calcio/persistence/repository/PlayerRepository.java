package com.rdiachuk.edu.calcio.persistence.repository;

import com.rdiachuk.edu.calcio.persistence.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by roman.diachuk on 3/15/2017.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByFirstNameAndLastName(String firstName, String lastName);
}
