import dto.MeasurementDTO;
import dto.MeasurementsResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GetClient {
    public static void main(String[] args) {
        List<MeasurementDTO> measurements = getAllMeasurements();
        for(MeasurementDTO measurement : measurements)
            System.out.println("Сенсор: " + measurement.getSensor().getName() + " - " +
                    "температура: " + measurement.getValue() + ", " +
                    (measurement.isRaining() ? "зарегистрирован дождь." : "дождь не зарегистрирован."));

        System.out.println(getRainyDaysCount());
    }

    public static List<MeasurementDTO> getAllMeasurements(){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements";

        MeasurementsResponse measurementsResponse = restTemplate.getForObject(url, MeasurementsResponse.class);

        return measurementsResponse.getMeasurements();
    }

    public static Long getRainyDaysCount(){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements/rainyDaysCount";

        return restTemplate.getForObject(url, Long.class);
    }
}
