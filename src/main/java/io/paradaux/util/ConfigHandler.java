package io.paradaux.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.nio.file.Path;
import java.util.List;

public class ConfigHandler {

    private static final String CONFIG_LOCATION = "sylvester.conf";
    private static final Logger logger = LoggerFactory.getLogger(ConfigHandler.class);

    private static Config config;
    public static Config getConfig() {
        return config;
    }

    private static HoconConfigurationLoader loader;
    private static CommentedConfigurationNode rootNode;
    public static CommentedConfigurationNode getRootNode() {
        return rootNode;
    }

    public static Config loadConfig() {
        IOUtils.deployFiles(logger);

        loader = HoconConfigurationLoader.builder()
            .path(Path.of(CONFIG_LOCATION)) // or url(), or source/sink
            .build();

        try {
            rootNode = loader.load();
            config = rootNode.get(Config.class); // Populate object
        } catch (ConfigurateException e) {
            logger.error("An error occurred while trying to read the configuration: " + e.getMessage());
            System.exit(1);
            return null;
        }

        logger.info("Successfully loaded configuration.");
        return config;
    }

    public static Config saveAndReloadConfig() {
        try {
            loader.save(rootNode); // Write to the original file
        } catch (ConfigurateException e) {
            logger.error("An error occurred while trying to read the configuration: " + e.getMessage());
            System.exit(1);
            return null;
        }

        logger.info("Successfully saved configuration.");
        return loadConfig();
    }

    @ConfigSerializable
    public static class Config {
        private BotConfig bot;
        private OpenAIConfig openai;

        public BotConfig bot() {
            return bot;
        }

        public OpenAIConfig openai() {
            return openai;
        }
    }

    @ConfigSerializable
    public static class BotConfig {
        private String token;
        private boolean respondOnMention;
        private boolean respondOnReply;
        private String[] wakeWords;
        private double randomResponseChance;
        private int messageHistoryLength;
        private List<String> listeningChannels;
        private String[] disabledGuilds;
        private String[] disabledChannels;

        public String token() {
            return token;
        }

        public boolean respondOnMention() {
            return respondOnMention;
        }

        public boolean respondOnReply() {
            return respondOnReply;
        }

        public String[] wakeWords() {
            return wakeWords;
        }

        public double randomResponseChance() {
            return randomResponseChance;
        }

        public int messageHistoryLength() {
            return messageHistoryLength;
        }

        public List<String> listeningChannels() {
            return listeningChannels;
        }

        public String[] disabledGuilds() {
            return disabledGuilds;
        }

        public String[] disabledChannels() {
            return disabledChannels;
        }
    }

    @ConfigSerializable
    public static class OpenAIConfig {
        private String token;
        private String model;
        private int maxTokens;
        private double temperature;
        private double topP;
        private String prompt;

        public String token() {
            return token;
        }

        public String model() {
            return model;
        }

        public int maxTokens() {
            return maxTokens;
        }

        public double temperature() {
            return temperature;
        }

        public double topP() {
            return topP;
        }

        public String prompt() {
            return prompt;
        }
    }

}
