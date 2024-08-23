package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {

    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    private ObjectMapper objectMapper;

    public String getWeatherForLocation(String keyword) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.weatherapi.com/v1/current.json?key=22f09f58ad164228913153242242308&q=" + keyword + "&aqi=no")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String res = response.body().string();

        WeatherResponse weatherResponse = objectMapper.readValue(res, WeatherResponse.class);

        // Extract the desired values
        String name = weatherResponse.getLocation().getName();
        String region = weatherResponse.getLocation().getRegion();
        String country = weatherResponse.getLocation().getCountry();
        String localtime = weatherResponse.getLocation().getLocaltime();
        double tempC = weatherResponse.getCurrent().getTemp_c();
        String conditionText = weatherResponse.getCurrent().getCondition().getText();
        double feelslikeC = weatherResponse.getCurrent().getFeelslike_c();

        // Return the extracted values as a formatted string
        return "Location: " + name + ", " + region + ", " + country + "\n" +
                "Local Time: " + localtime + "\n" +
                "Temperature: " + tempC + "°C\n" +
                "Condition: " + conditionText + "\n" +
                "Feels Like: " + feelslikeC + "°C";
    }

    public static class WeatherResponse {
        private Location location;
        private Current current;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Current getCurrent() {
            return current;
        }

        public void setCurrent(Current current) {
            this.current = current;
        }

        public static class Location {
            private String name;
            private String region;
            private String country;
            private String localtime;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLocaltime() {
                return localtime;
            }

            public void setLocaltime(String localtime) {
                this.localtime = localtime;
            }
        }

        public static class Current {
            private double temp_c;
            private Condition condition;
            private double feelslike_c;

            public double getTemp_c() {
                return temp_c;
            }

            public void setTemp_c(double temp_c) {
                this.temp_c = temp_c;
            }

            public Condition getCondition() {
                return condition;
            }

            public void setCondition(Condition condition) {
                this.condition = condition;
            }

            public double getFeelslike_c() {
                return feelslike_c;
            }

            public void setFeelslike_c(double feelslike_c) {
                this.feelslike_c = feelslike_c;
            }

            public static class Condition {
                private String text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }
        }
    }
}
