package ru.semenova.springcourse.RESTApplicationAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.semenova.springcourse.RESTApplicationAPI.dto.SensorDTO;
import ru.semenova.springcourse.RESTApplicationAPI.models.Sensor;
import ru.semenova.springcourse.RESTApplicationAPI.services.SensorsService;
import ru.semenova.springcourse.RESTApplicationAPI.utils.ErrorResponse;
import ru.semenova.springcourse.RESTApplicationAPI.utils.SensorNotCreatedException;
import ru.semenova.springcourse.RESTApplicationAPI.utils.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");

            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorsService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotCreatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
