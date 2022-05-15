package com.tjhelmuth.twitchchatrecorder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(args = {"--twitch.channel=xqcow", "--twitch.token=a"})
public class TwitchChatRecorderApplicationTest extends BaseSpringTest {
    @Autowired
    private TwitchConfig twitchConfig;

    @Test
    public void getsConfigFromCommandLine(){
        assertThat(twitchConfig.getChannel()).isEqualTo("xqcow");
        assertThat(twitchConfig.getToken()).isEqualTo("a");
    }
}