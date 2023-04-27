package ru.semenova.springcourse.RESTApplicationAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semenova.springcourse.RESTApplicationAPI.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
