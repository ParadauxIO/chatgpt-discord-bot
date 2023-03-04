package io.paradaux.util;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.*;

public class ConfigHandler {

    private static final String CONFIG_LOCATION = "/config.json";

    private static Config config;
    public static Config getConfig() {
        return config;
    }

    public static Config loadConfig() {
        if (config != null) {
            throw new IllegalStateException("Configuration has already been loaded.");
        }

        try (Reader reader = new InputStreamReader(ConfigHandler.class.getResourceAsStream(CONFIG_LOCATION))) {
            config = new Gson().fromJson(reader, Config.class);

            if (config == null) {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load configuration: " + e.getMessage());
        }

        return config;
    }

    // Credit to chatgpt writing this for me -- I was too lazy
    public static String getFileContentsAsString(String file) {
        // Get the path of the resource file
        InputStream inputStream = ConfigHandler.class.getClassLoader().getResourceAsStream(file);

        if (inputStream == null) {
            throw new IllegalStateException("File does not exist");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // Read the file content line by line and append it to the StringBuilder
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.separator"));
            }

            // Convert the StringBuilder to a String and return
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
