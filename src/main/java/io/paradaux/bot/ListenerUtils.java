package io.paradaux.bot;

import io.paradaux.util.ConfigHandler;

public class ListenerUtils {

    private static final ConfigHandler.Config config = ConfigHandler.getConfig();
    public static boolean isDisabledHere(String channel, String guild) {
        return config.bot().disabledChannels().contains(channel)
                || config.bot().disabledGuilds().contains(guild);
    }
}
