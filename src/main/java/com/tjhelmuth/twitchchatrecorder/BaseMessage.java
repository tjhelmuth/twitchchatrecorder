package com.tjhelmuth.twitchchatrecorder;

public interface BaseMessage {
    String getContents();

    default String print(){
        return this.toString();
    }
}
