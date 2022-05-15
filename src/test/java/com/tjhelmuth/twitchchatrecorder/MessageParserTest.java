package com.tjhelmuth.twitchchatrecorder;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MessageParserTest {

    @Test
    public void parsesMessage(){
        String messageContents = "@badge-info=subscriber/3;badges=subscriber/3;client-nonce=2045b939cb4f9f1afceae6b69017ca52;color=#00FF7F;display-name=Yalex3223;emotes=;first-msg=0;flags=;id=3e1536ef-e677-4803-a03b-c3eb05ddfe66;mod=0;room-id=51496027;subscriber=1;tmi-sent-ts=1651795731563;turbo=0;user-id=113656956;user-type= :yalex3223!yalex3223@yalex3223.tmi.twitch.tv PRIVMSG #loltyler1 :@saebur__ reformed AYAYA Clap";
        List<BaseMessage> messages = MessageParser.parseMessages(messageContents);

        assertThat(messages)
                .hasSize(1)
                .extracting(base -> (ChatMessage)base)
                .containsExactly(new ChatMessage("Yalex3223", "00FF7F", "@saebur__ reformed AYAYA Clap"));
    }

}
























































