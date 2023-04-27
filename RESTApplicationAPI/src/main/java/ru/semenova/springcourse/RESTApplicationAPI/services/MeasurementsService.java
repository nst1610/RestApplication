package ru.semenova.springcourse.RESTApplicationAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semenova.springcourse.RESTApplicationAPI.models.Measurement;
import ru.semenova.springcourse.RESTApplicationAPI.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement){
        enrich(measurement);
        measurementsRepository.save(measurement);
    }

    public Long rainyDaysCount(){
        return measurementsRepository.findAll().stream().filter(Measurement::isRaining).count();
    }

    private void enrich(Measurement measurement){
        measurement.setSensor(sensorsService.findOneByNameOrElseThrowException(measurement.getSensor().getName()));
        measurement.setCreatedAt(LocalDateTime.now());
    }

}
