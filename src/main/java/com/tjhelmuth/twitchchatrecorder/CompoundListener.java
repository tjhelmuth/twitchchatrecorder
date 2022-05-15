package com.tjhelmuth.twitchchatrecorder;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Primary
@Component
public class CompoundListener implements MessageListener {
    private final List<MessageListener> listeners;

    @Override
    public void handleMessage(ChatMessage m) {
        listeners.forEach(l -> l.handleMessage(m));
    }
}
