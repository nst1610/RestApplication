package ru.semenova.springcourse.RESTApplicationAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semenova.springcourse.RESTApplicationAPI.models.Sensor;
import ru.semenova.springcourse.RESTApplicationAPI.repositories.SensorsRepository;
import ru.semenova.springcourse.RESTApplicationAPI.utils.SensorNotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public void save(Sensor sensor){
        sensorsRepository.save(sensor);
    }

    public Sensor findOneByNameOrElseThrowException(String name){
        return sensorsRepository.findByName(name).orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> findOneByName(String name){
        return sensorsRepository.findByName(name);
    }
}
