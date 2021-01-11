package com.sndy.spectre.integration.discord;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Appender extends AbstractAppender {

    public Appender() {
        // do your calculations here before starting to capture
        super("SpectreLogger", null, null);
        start();
    }

    @Override
    public void append(LogEvent event) {
        LogEvent log = event.toImmutable();
        String message = log.getMessage().getFormattedMessage();
        SimpleDateFormat formatter = new SimpleDateFormat();
        message = "[" + formatter.format(new Date(event.getTimeMillis())) + " " + event.getLevel().toString() + "] " + message;
        message = message.replaceAll("§.", "");
        if(!message.contains("logged in with entity id")) {
            Objects.requireNonNull(SpectreBot.getContext().getTextChannelById(Channels.CONSOLE)).sendMessage("`" + message + "`").queue();
        }
    }

}
