package io.paradaux.openai;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import io.paradaux.util.ConfigHandler;
import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;

public class ChatGPTImpl {

    private static final ConfigHandler.Config config = ConfigHandler.getConfig();

    private final OpenAiService service;

    // Map between channel IDs and their respective histories
    private final HashMap<String, GPTMessageQueue> messageHistory;

    public ChatGPTImpl() {
        // Set the API key
        messageHistory = new HashMap<>();
        service = new OpenAiService(config.openai().token());
    }

    public String respond(Message message) {
        if (!messageHistory.containsKey(message.getChannel().getId())) {
            messageHistory.put(message.getChannel().getId(), new GPTMessageQueue(config.bot().messageHistoryLength(),
                    config.openai().prompt()));
        }

        String formattedMessage = String.format("%s#%s: %s", message.getAuthor().getName(),
                message.getAuthor().getDiscriminator(), message.getContentRaw());

        return sendRequest(ChatMessageRole.USER.value(), formattedMessage, message.getChannel().getId())
                .getMessage()
                .getContent();
    }

    private ChatCompletionChoice sendRequest(String role, String message, String channel) {
        // If it's a system message then reset the message history
        if (role.equals(ChatMessageRole.SYSTEM.value())) {
            messageHistory.put(channel, new GPTMessageQueue(config.bot().messageHistoryLength(), config.openai().prompt()));
        }

        // Get the message history
        GPTMessageQueue history = messageHistory.get(channel);

        // Add our new message to the message history
        history.queue(new ChatMessage(role, message));

        // Build the GPT request object
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(history.toList())
                .maxTokens(config.openai().maxTokens())
                .temperature(config.openai().temperature())
                .topP(config.openai().topP())
                .model(config.openai().model())
                .build();

        ChatCompletionChoice result = service.createChatCompletion(request).getChoices().get(0);
        history.queue(result.getMessage());
        return result;
    }
}