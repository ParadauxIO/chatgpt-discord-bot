package io.paradaux.openai;

import io.paradaux.util.ConfigHandler;
import io.paradaux.util.IOUtils;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Map;

public class ChatCache {

    private static final ConfigHandler.Config config = ConfigHandler.getConfig();

    // A map of Discord Guild IDs to their respective Chats
    private static Map<String, ChatGPTImpl> chats = new HashMap<>();

    public static String respond(String guild, User user, String message) {
        // Get the ChatGPT instance belonging to this server
        ChatGPTImpl chatgpt = chats.get(guild);

        // create a new one if it doesn't exist in the cache
        if (chatgpt == null) {
            chatgpt = new ChatGPTImpl(IOUtils.getFileContentsAsString("prompt.txt"),
                    config.getOpenaiToken());

            // Save the newly created ChatGPT instance.
            chats.put(guild, chatgpt);
        }

        String rawResponse = chatgpt.respond(String.format("%s#%s: %s", user.getName(), user.getDiscriminator(), message));
        return rawResponse.replace("Sylvester: ", "").trim();
    }
}
