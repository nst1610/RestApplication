import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RestApplicationClient {
    public static void main(String[] args) {
        final String sensorName = "Sensor001";
        registerSensor(sensorName);

        for(int i = 0; i < 1000; i++){
            sendMeasurement(Math.round((-100 + Math.random() * 200) *100) / 100.00,
                    new Random().nextBoolean(), sensorName);
        }
    }

    public static void registerSensor(String sensorName){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/sensors/registration";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        HttpEntity<Map<String, Object>> registrationRequest = new HttpEntity<>(jsonData, headers);
        try{
            restTemplate.postForObject(url, registrationRequest, String.class);
        } catch (HttpClientErrorException e){
            System.out.println(e.getMessage());
        }
    }

    public static void sendMeasurement(double value, boolean raining, String sensorName){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        HttpEntity<Map<String, Object>> registrationRequest = new HttpEntity<>(jsonData, headers);

        try{
            restTemplate.postForObject(url, registrationRequest, String.class);
        } catch (HttpClientErrorException e){
            System.out.println(e.getMessage());
        }
    }
}
