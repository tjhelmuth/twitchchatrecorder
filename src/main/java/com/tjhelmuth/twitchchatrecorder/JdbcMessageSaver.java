package com.tjhelmuth.twitchchatrecorder;


import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class JdbcMessageSaver implements MessageListener {
    private static final int BATCH_SIZE = 1000;
    private static final String INSERT_QUERY = """
            insert into twitch_logs(username, color, message) VALUES (?, ?, ?)
            """;
    private final BlockingQueue<ChatMessage> messageQueue = new LinkedBlockingQueue<>();

    private final ExecutorService executorService;
    private final JdbcTemplate jdbcTemplate;

    public JdbcMessageSaver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void handleMessage(ChatMessage m) {
        messageQueue.add(m);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void startSaving(){
        executorService.execute(() -> {
            while(!Thread.interrupted()){
                try {
                    this.persistMessages();
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Transactional
    public void persistMessages() throws InterruptedException {

        List<ChatMessage> batch = new ArrayList<>();
        Queues.drain(messageQueue, batch, BATCH_SIZE, Duration.ofSeconds(30));

        StopWatch watch = new StopWatch();
        watch.start();

        List<Object[]> args = batch.stream()
                        .map(msg -> new Object[]{msg.username(), msg.color(), msg.contents()})
                        .toList();

        if(args.isEmpty()){
            return;
        }

        jdbcTemplate.batchUpdate(INSERT_QUERY, args);
        watch.stop();
        log.debug("Inserted {} logs in {} ms", batch.size(), watch.getTotalTimeMillis());
    }

    @PreDestroy
    public void destroy(){
        executorService.shutdown();
    }
}
