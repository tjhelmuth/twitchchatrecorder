package com.tjhelmuth.twitchchatrecorder;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class TwitchChatRecorderApplication implements CommandLineRunner {
    private final TwitchConfig twitchConfig;

    public static void main(String[] args) {
        SpringApplication.run(TwitchChatRecorderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(Strings.isBlank(twitchConfig.getChannel())){
            throw new IllegalArgumentException("must set twitch.channel");
        }
    }
}
