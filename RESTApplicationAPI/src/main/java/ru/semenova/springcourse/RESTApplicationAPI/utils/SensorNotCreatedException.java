package ru.semenova.springcourse.RESTApplicationAPI.utils;

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String message){
        super(message);
    }
}
