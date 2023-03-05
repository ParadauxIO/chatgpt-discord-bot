package io.paradaux.openai;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.ArrayList;
import java.util.List;

public class GPTMessageQueue {

    private final List<ChatMessage> messages;
    private final int size;

    public GPTMessageQueue(int size, String prompt) {
        this.messages = new ArrayList<>();
        this.size = size;
        queue(new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt));
    }

    public void queue(ChatMessage entry) {
        // Maintain the desired length
        if (messages.size() >= size) {
            // Remove the second entry, first contains the prompt
            messages.remove(1);
        }

        messages.add(entry);
    }

    public List<ChatMessage> toList() {
        return messages;
    }

}
