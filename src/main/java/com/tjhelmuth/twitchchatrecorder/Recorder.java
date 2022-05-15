package com.tjhelmuth.twitchchatrecorder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class Recorder {
    private final TwitchWsClient client;
    private final TwitchConfig config;

    @PostConstruct
    public void setup(){
        start();
    }

    public void start(){
        client.connect();
    }

    public void stop(){
        client.close();
    }
}
