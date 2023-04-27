package ru.semenova.springcourse.RESTApplicationAPI.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.semenova.springcourse.RESTApplicationAPI.models.Sensor;
import ru.semenova.springcourse.RESTApplicationAPI.services.SensorsService;

@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
         if (sensorsService.findOneByName(((Sensor) target).getName()).isPresent())
             errors.rejectValue("name", "", "Сенсор с таким названием уже зарегистрирован!");
    }
}
