package com.tjhelmuth.twitchchatrecorder;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;


@Slf4j
@Component
public class TwitchWsClient extends WebSocketClient {
    private final MessageListener listener;
    private final TwitchConfig twitchConfig;

    public TwitchWsClient(TwitchConfig config, MessageListener listener) {
        super(URI.create(config.getUrl()));
        this.listener = listener;
        this.twitchConfig = config;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info("Connection opened.");

        String pass = "PASS oauth:%s".formatted(twitchConfig.getToken());
        log.info("Pass: {}", pass);
        this.send(pass);
        this.send("NICK sealpuncher");
        this.send("CAP REQ :twitch.tv/commands twitch.tv/tags");
        this.send("JOIN #%s".formatted(twitchConfig.getChannel()));
    }

    @Override
    public void onMessage(String rawMessage) {
        if(log.isTraceEnabled()){
            log.trace(rawMessage);
        }

        try {
            List<BaseMessage> messages = MessageParser.parseMessages(rawMessage);

            boolean hasPing = false;
            for(var msg : messages){
                if(msg instanceof PingMessage){
                    hasPing = true;
                    continue;
                }

                if(msg instanceof ChatMessage m){
                    listener.handleMessage(m);
                }
            }

            if(hasPing){
                this.send("PONG :tmi.twitch.tv");
            }

        } catch (Exception e){
            log.error("Error parsing message {}", rawMessage, e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("Connection closed. Exiting app.");
    }

    @Override
    public void onError(Exception ex) {
        log.warn("Error in connection. Closing app.", ex);
    }
}
