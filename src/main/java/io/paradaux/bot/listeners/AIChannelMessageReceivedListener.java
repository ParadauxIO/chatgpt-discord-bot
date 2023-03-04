package io.paradaux.bot.listeners;

import io.paradaux.openai.ChatGPTImpl;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

public class AIChannelMessageReceivedListener implements EventListener {

    private final String listeningChannel;
    private final ChatGPTImpl chatGPT;

    public AIChannelMessageReceivedListener(String listeningChannel, ChatGPTImpl chatGPT) {
        this.listeningChannel = listeningChannel;
        this.chatGPT = chatGPT;
    }

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
         if (genericEvent instanceof MessageReceivedEvent event) {
            if (!event.getChannel().getId().equals(listeningChannel)) {
                return;
            }

            if (event.getAuthor().isBot()) {
                return;
            }

            String message = event.getMessage().getContentRaw();
            String response = chatGPT.respond(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + ": " + message);
            event.getMessage().reply(response).complete();
        }
    }
}
