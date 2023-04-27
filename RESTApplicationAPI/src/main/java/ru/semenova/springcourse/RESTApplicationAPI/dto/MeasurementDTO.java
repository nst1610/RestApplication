package ru.semenova.springcourse.RESTApplicationAPI.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull
    @Min(value = -100, message = "Значение температуры должно быть не меньше -100.")
    @Max(value = 100, message = "Значение температуры должно быть не больше 100.")
    private double value;

    @NotNull
    private boolean raining;

    @NotNull
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
