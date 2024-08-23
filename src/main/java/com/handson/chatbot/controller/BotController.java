package com.handson.chatbot.controller;

import com.handson.chatbot.service.ImdbService;
import com.handson.chatbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bot")
public class BotController {

    @Autowired
    ImdbService imdbService; // Ensure this matches the service name

    @Autowired
    WeatherService weatherService;

    @RequestMapping(value = "/imdb", method = RequestMethod.GET)
    public ResponseEntity<?> getMovie(@RequestParam String keyword) throws IOException {
        // Use the instance variable to call the non-static method
        return new ResponseEntity<>(imdbService.searchMovie(keyword), HttpStatus.OK);
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ResponseEntity<?> getWeather(@RequestParam String keyword) throws IOException {
        // Use the instance variable to call the non-static method
        return new ResponseEntity<>(weatherService.getWeatherForLocation(keyword), HttpStatus.OK);
    }
}
