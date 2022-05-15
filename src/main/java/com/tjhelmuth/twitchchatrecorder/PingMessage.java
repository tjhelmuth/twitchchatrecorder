package com.tjhelmuth.twitchchatrecorder;

public record PingMessage(String nonce) implements BaseMessage {
    @Override
    public String getContents() {
        return nonce;
    }
}
