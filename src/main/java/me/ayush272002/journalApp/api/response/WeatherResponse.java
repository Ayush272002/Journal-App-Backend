package me.ayush272002.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse{
    private Current current;

    @Getter
    @Setter
    public static class Current{
        private int temperature;
        @JsonProperty("weather_description")
        private List<String> weatherDescriptions;
        private int feelslike;
    }

}



