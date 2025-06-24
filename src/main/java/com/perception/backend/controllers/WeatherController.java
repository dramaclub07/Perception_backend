package com.perception.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather API", description = "Get current weather information")
public class WeatherController {

    private final String API_KEY = "REPLACE_WITH_YOUR_OPENWEATHERMAP_API_KEY"; // TODO: Set your real API key here
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Operation(summary = "Get current weather by city name")
    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam String city, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        // Optionally check for JWT in the Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            // You can add JWT validation logic here if needed
        }
        String url = String.format("%s?q=%s&appid=%s&units=metric", BASE_URL, city, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not fetch weather data: " + e.getMessage());
        }
    }
}
