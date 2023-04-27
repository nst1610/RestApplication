package ru.semenova.springcourse.RESTApplicationAPI.utils;

public class MeasurementNotAddedException extends RuntimeException {
    public MeasurementNotAddedException(String message){
        super(message);
    }
}
