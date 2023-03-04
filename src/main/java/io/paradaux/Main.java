package io.paradaux;

import io.paradaux.bot.DiscordBot;
import io.paradaux.util.ConfigHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("ChatGPT Discord Bot v1.0");

        var config = ConfigHandler.loadConfig();
        var bot = new DiscordBot(config);

    }
}