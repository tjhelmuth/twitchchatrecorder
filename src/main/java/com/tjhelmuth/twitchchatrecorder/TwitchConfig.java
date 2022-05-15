package com.tjhelmuth.twitchchatrecorder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("twitch")
@Data
public class TwitchConfig {
    private String url = "ws://irc-ws.chat.twitch.tv:80";
    private String channel;
    private String token;
}
