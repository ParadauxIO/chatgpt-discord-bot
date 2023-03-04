package io.paradaux.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;

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

            config = gson.fromJson(bufferedReader, Config.class);
            return config;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config");
        }
    }

    public static class Config {
        @SerializedName("discord_token")
        private String discordToken;

        @SerializedName("openai_token")
        private String openaiToken;

        private HashMap<String, String> guilds;

        public String getDiscordToken() {
            return discordToken;
        }

        public String getOpenaiToken() {
            return openaiToken;
        }

        public HashMap<String, String> getGuilds() {
            return guilds;
        }
    }

}
