package com.tjhelmuth.twitchchatrecorder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OnlyDbTest {
    @MockBean
    public TwitchWsClient wsClient;

    @Autowired public Optional<FileWritingListener> fileWriter;
    @Autowired public Optional<PrintingListener> sysOutWriter;
    @Autowired public Optional<JdbcMessageSaver> postgresWriter;

    @Test
    public void onlyDbWriterLoadedByDefault(){
        assertThat(fileWriter).isEmpty();
        assertThat(sysOutWriter).isEmpty();
        assertThat(postgresWriter).isPresent();
    }
}
