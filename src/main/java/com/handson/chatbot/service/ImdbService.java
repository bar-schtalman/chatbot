package com.handson.chatbot.service;

import org.springframework.stereotype.Service;

@Service
public class ImdbService {

    public String searchMovie(String keyword) {
        return "Searched for:" + keyword;
    }
}
