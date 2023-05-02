import dto.MeasurementDTO;
import dto.MeasurementsResponse;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetClient {
    public static void main(String[] args) {
        List<MeasurementDTO> measurements = getAllMeasurements();
        for(MeasurementDTO measurement : measurements)
            System.out.println("Сенсор: " + measurement.getSensor().getName() + " - " +
                    "температура: " + measurement.getValue() + ", " +
                    (measurement.isRaining() ? "зарегистрирован дождь." : "дождь не зарегистрирован."));

        System.out.println(getRainyDaysCount());
        drawChart();
    }

    public static List<MeasurementDTO> getAllMeasurements(){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements";

        MeasurementsResponse measurementsResponse = restTemplate.getForObject(url, MeasurementsResponse.class);

        if(measurementsResponse.getMeasurements() == null)
            return Collections.emptyList();

        return measurementsResponse.getMeasurements();
    }

    public static Long getRainyDaysCount(){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements/rainyDaysCount";

        return restTemplate.getForObject(url, Long.class);
    }

    public static void drawChart(){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements";

        List<Double> temperatures = getAllMeasurements().stream().map(MeasurementDTO::getValue)
                .collect(Collectors.toList());

        XYChart chart = QuickChart.getChart("График температур", "x", "y",
                "temperature", IntStream.range(0, temperatures.size()).asDoubleStream().toArray(),
                temperatures.stream().mapToDouble(t -> t).toArray());

        new SwingWrapper(chart).displayChart();
    }
}
