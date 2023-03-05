package io.paradaux.bot;

import io.paradaux.bot.listeners.AIChannelMessageReceivedListener;
import io.paradaux.bot.listeners.ReadyListener;
import io.paradaux.util.ConfigHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {

    private final JDA client;
    private final ConfigHandler.Config config;

    public DiscordBot(ConfigHandler.Config config) {
        this.config = config;
        this.client = createClient();
    }

    private JDA createClient() {
        return JDABuilder.createDefault(config.bot().token()) // enable all default intents
                .enableIntents(GatewayIntent.MESSAGE_CONTENT) // also enable privileged intent
                .addEventListeners(new ReadyListener())
                .addEventListeners(new AIChannelMessageReceivedListener())
                .build();
    }
}
