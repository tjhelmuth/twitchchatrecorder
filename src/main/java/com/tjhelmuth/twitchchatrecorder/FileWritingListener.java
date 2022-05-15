package com.tjhelmuth.twitchchatrecorder;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@ConditionalOnProperty(prefix = "output", value = "file")
@Component
public class FileWritingListener implements MessageListener {
    private final FileWriter fileWriter;

    public FileWritingListener() {
        try {
            this.fileWriter = new FileWriter("chatlog.txt", true);
        } catch (IOException e){
            log.error("Unable to open file", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleMessage(ChatMessage m) {
        String csv = m.username() + "," + (m.color() == null ? "" : m.color()) + ",\"" + m.contents() + "\"";
        try {
            fileWriter.append(csv).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
