package com.tjhelmuth.twitchchatrecorder;

@FunctionalInterface
public interface MessageListener {
    void handleMessage(ChatMessage m);
}
