package io.paradaux.openai;

import com.google.gson.GsonBuilder;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import io.paradaux.util.ConfigHandler;
import io.paradaux.util.IOUtils;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatGPTImpl {

    private final OpenAiService service;

    // Map between channel IDs and their respective histories
    private final HashMap<String, GPTMessageQueue> messageHistory;
    private List<ChatMessage> messages;
    private final String prompt;

    public ChatGPTImpl(String prompt, String openaiToken) {
        this.prompt = prompt;
        this.messageHistory = new HashMap<>();

        // Set the API key
        service = new OpenAiService(openaiToken);
    }

    public String respond(Message message) {
        if (!messageHistory.containsKey(message.getChannel().getId())) {
            messageHistory.put(message.getChannel().getId(), new GPTMessageQueue(10, prompt));
        }

        return sendRequest(ChatMessageRole.USER.value(), message.getContentRaw(), message.getChannel().getId())
                .getMessage()
                .getContent();
    }

    private ChatCompletionChoice sendRequest(String role, String message, String channel) {
        // If it's a system message then reset the message history
        if (role.equals(ChatMessageRole.SYSTEM.value())) {
            messages = new ArrayList<>();
        }

        // Add our new message to the message history
        messages.add(new ChatMessage("user", message));

        // Build the GPT request object
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .messages(messages)
                .maxTokens(150)
                .temperature(0.6)
                .topP(0.8)
                .model("gpt-3.5-turbo")
                .build();

        ChatCompletionChoice result = service.createChatCompletion(request).getChoices().get(0);
        messageHistory.()
        messages.add(result.getMessage());
        return result;
    }
}