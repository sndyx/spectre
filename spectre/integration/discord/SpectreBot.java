package com.sndy.spectre.integration.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;

import javax.security.auth.login.LoginException;

public class SpectreBot {

    public static JDA bot;

    public static void initBot() throws LoginException {
        bot = JDABuilder.createDefault("Nzk1ODI1MzM3MDUzMDg1Njk3.X_PAGg.gQ1ZaDOgX1WNlAF0EbdVoGUd_ZU").build();
        bot.getPresence().setStatus(OnlineStatus.ONLINE);
        bot.getPresence().setActivity(Activity.watching("over the server!"));
        bot.addEventListener(new Console());
    }

    public static Guild getContext(){
        return bot.getGuildById("624030322882773062");
    }

}
