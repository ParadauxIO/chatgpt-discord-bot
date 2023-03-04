package io.paradaux.openai;

import com.google.gson.GsonBuilder;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import io.paradaux.util.ConfigHandler;
import io.paradaux.util.IOUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTImpl {

    private final OpenAiService service;
    private List<ChatMessage> messages;
    private String listeningChannel;
    private String prompt;

    public ChatGPTImpl(String listeningChannel, String prompt, String openaiToken) {
        this.listeningChannel = listeningChannel;
        this.prompt = prompt;

        // Set the API key
        service = new OpenAiService(openaiToken);

        // Spawn a new ChatGPT session with the system prompt.
        init();
    }

    public String init() {
        return sendRequest(ChatMessageRole.SYSTEM.value(), prompt)
                .getMessage()
                .getContent();
    }

    public String respond(String message) {
        return sendRequest(ChatMessageRole.USER.value(), message)
                .getMessage()
                .getContent();
    }

    private ChatCompletionChoice sendRequest(String role, String message) {
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
        messages.add(result.getMessage());
        return result;
    }
}