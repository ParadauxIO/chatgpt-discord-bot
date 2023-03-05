package io.paradaux.openai;

import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;
import java.util.Map;

public class ChatCache {

    // A map of Discord Guild IDs to their respective Chats
    private static final Map<String, ChatGPTImpl> chats = new HashMap<>();

    public static String respond(Message message) {
        // Get the ChatGPT instance belonging to this server
        ChatGPTImpl chatgpt = chats.get(message.getGuild().getId());

        // create a new one if it doesn't exist in the cache
        if (chatgpt == null) {
            chatgpt = new ChatGPTImpl();

            // Save the newly created ChatGPT instance.
            chats.put(message.getGuild().getId(), chatgpt);
        }

        String rawResponse = chatgpt.respond(message);
        return rawResponse.replace("Sylvester: ", "").trim();
    }
}
