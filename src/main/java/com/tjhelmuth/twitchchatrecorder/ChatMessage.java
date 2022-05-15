package com.tjhelmuth.twitchchatrecorder;

public record ChatMessage(
        String username,
        String color,
        String contents
) implements BaseMessage {
    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public String print(){
        return "%s:  %s".formatted(username, contents);
    }
}
