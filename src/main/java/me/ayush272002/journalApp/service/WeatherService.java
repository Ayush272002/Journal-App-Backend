package me.ayush272002.journalApp.service;

import me.ayush272002.journalApp.api.response.WeatherResponse;
import me.ayush272002.journalApp.cache.AppCache;
import me.ayush272002.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;

    private final AppCache appCache;
    private final RedisService redisService;
    private final RestTemplate restTemplate;

    public WeatherService(AppCache appCache, RedisService redisService, RestTemplate restTemplate) {
        this.appCache = appCache;
        this.redisService = redisService;
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            System.out.println("Got from cache");
            return weatherResponse;
        }

        String finalApi = appCache.getAPP_CACHE().get("weather_api").replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, API_KEY);
        ResponseEntity<WeatherResponse> res = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = res.getBody();

        if (body != null) redisService.set("weather_of_" + city, body, 300L);

        return body;
    }
}
