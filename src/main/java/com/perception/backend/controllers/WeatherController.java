package com.perception.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.HttpHeaders; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather API", description = "Get current weather information")
public class WeatherController {

    @Value("${weather.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Operation(summary = "Get current weather by city name or coordinates")
    @GetMapping
    public ResponseEntity<?> getWeather(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);   
            // Optionally validate JWT here
        }
        String url;
        if (lat != null && lon != null) {
            url = String.format("%s?lat=%f&lon=%f&appid=%s", BASE_URL, lat, lon, API_KEY);
        } else if (city != null && !city.isEmpty()) {
            url = String.format("%s?q=%s&appid=%s", BASE_URL, city, API_KEY);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must provide either city or lat/lon");
        }
        url += "&units=metric";
        RestTemplate restTemplate = new RestTemplate();
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not fetch weather data: " + e.getMessage());
        }
    }
}
