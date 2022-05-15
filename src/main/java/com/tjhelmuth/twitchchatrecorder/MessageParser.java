package com.tjhelmuth.twitchchatrecorder;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@UtilityClass
public class MessageParser {

    public List<BaseMessage> parseMessages(String message){
        List<BaseMessage> messages = new ArrayList<>();
        String[] lines = message.split("\\r?\\n");
        for(var line : lines){
            parseLine(line)
                    .ifPresent(messages::add);
        }
        return messages;
    }

    private Optional<BaseMessage> parseLine(String line){
        return getMessageType(line)
                .flatMap(msgType -> Optional.ofNullable(parseMessage(line, msgType)));
    }

    private BaseMessage parseMessage(String line, MessageType type){
        return switch (type) {
            case PING -> parsePing(line);
            case PRIVMSG -> parseMessage(line);
        };
    }

    private PingMessage parsePing(String line){
        String[] parts = line.split(" ");

        return new PingMessage("idk man");
    }

    private ChatMessage parseMessage(String line){
        String[] parts = line.split(" ", 5);

        if(parts.length < 5){
            log.warn("Message has less than expected parts, {}", line);
            return null;
        }

        var tags = parseTags(parts[0]);
        String username = tags.get("display-name");
        if(username == null || username.isBlank()){
            return null;
        }

        String color = tags.get("color");
        color = color == null ? null : color.substring(1);

        return new ChatMessage(username, color, parts[4].substring(1).strip());
    }

    private Map<String, String> parseTags(String tagPart){
        Map<String, String> tags = new HashMap<>();
        String[] tagParts = tagPart.split(";");
        for(var part : tagParts){
            String[] kv = part.split("=");
            if(kv.length < 2) continue;
            tags.put(kv[0], kv[1]);
        }
        return tags;
    }

    private Optional<MessageType> getMessageType(String line){
        for(var messageType : MessageType.values()){
            if(line.contains(messageType.name())){
                return Optional.of(messageType);
            }
        }
        return Optional.empty();
    }

    private enum MessageType {
        PING,
        PRIVMSG
    }
}
