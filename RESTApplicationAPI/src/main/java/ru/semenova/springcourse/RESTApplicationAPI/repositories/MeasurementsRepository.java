package ru.semenova.springcourse.RESTApplicationAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semenova.springcourse.RESTApplicationAPI.models.Measurement;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

}
