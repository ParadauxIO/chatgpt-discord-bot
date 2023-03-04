package io.paradaux;

import io.paradaux.bot.DiscordBot;
import io.paradaux.openai.ChatGPTImpl;
import io.paradaux.util.ConfigHandler;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var config = ConfigHandler.loadConfig();
        var chatgpt = new ChatGPTImpl(config);
        var bot = new DiscordBot(config, chatgpt);

    }
}