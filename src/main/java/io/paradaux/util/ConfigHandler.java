package io.paradaux.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ConfigHandler {



    private static final String CONFIG_LOCATION = "/config.json";

    private static Config config;
    public static Config getConfig() {
        return config;
    }

    public static Config loadConfig() {
        try {
            IOUtils.deployFiles(LoggerFactory.getLogger(ConfigHandler.class));

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.json"));
            return gson.fromJson(bufferedReader, Config.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config");
        }
    }

    public static class Config {
        @SerializedName("discord_token")
        private String discordToken;

        @SerializedName("openai_token")
        private String openaiToken;

        @SerializedName("listening_channel")
        private String listeningChannel;

        public Config(String discordToken, String openaiToken, String listeningChannel) {
            this.discordToken = discordToken;
            this.openaiToken = openaiToken;
            this.listeningChannel = listeningChannel;
        }

        public String getDiscordToken() {
            return discordToken;
        }

        public String getOpenaiToken() {
            return openaiToken;
        }

        public String getListeningChannel() {
            return listeningChannel;
        }
    }

}
