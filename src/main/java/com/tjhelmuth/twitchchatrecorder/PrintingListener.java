package com.tjhelmuth.twitchchatrecorder;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Prints messages to system out
 */
@ConditionalOnProperty(prefix = "output", value = "console")
@Component
public class PrintingListener implements MessageListener {
    @Override
    public void handleMessage(ChatMessage m) {
        System.out.println(m.print());
    }
}
