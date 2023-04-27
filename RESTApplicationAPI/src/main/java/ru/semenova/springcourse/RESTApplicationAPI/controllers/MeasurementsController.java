package ru.semenova.springcourse.RESTApplicationAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.semenova.springcourse.RESTApplicationAPI.dto.MeasurementDTO;
import ru.semenova.springcourse.RESTApplicationAPI.dto.MeasurementsResponse;
import ru.semenova.springcourse.RESTApplicationAPI.models.Measurement;
import ru.semenova.springcourse.RESTApplicationAPI.services.MeasurementsService;
import ru.semenova.springcourse.RESTApplicationAPI.utils.ErrorResponse;
import ru.semenova.springcourse.RESTApplicationAPI.utils.MeasurementNotAddedException;
import ru.semenova.springcourse.RESTApplicationAPI.utils.SensorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements(){
        return new MeasurementsResponse(measurementsService.findAll()
                .stream().map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");

            throw new MeasurementNotAddedException(errorMsg.toString());
        }

        measurementsService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementsService.rainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotAddedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotFoundException e){
        ErrorResponse response = new ErrorResponse("Сенсор с таким названием не зарегистрирован!",
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
