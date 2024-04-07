package com.tqs108287.app.hw1_bustickets.repositories;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

}
