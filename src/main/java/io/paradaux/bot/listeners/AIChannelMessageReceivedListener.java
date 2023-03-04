package io.paradaux.bot.listeners;

import io.paradaux.openai.ChatCache;
import io.paradaux.openai.ChatGPTImpl;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AIChannelMessageReceivedListener implements EventListener {

    private final HashMap<String, String> guilds;

    public AIChannelMessageReceivedListener(HashMap<String, String> guilds) {
        this.guilds = guilds;
    }

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
         if (genericEvent instanceof MessageReceivedEvent event) {
             // If not in the correct channel for this guild
            if (!event.getChannel().getId().equals(guilds.get(event.getGuild().getId()))) {
                return;
            }

            // Don't respond to yourself or other bots!
            if (event.getAuthor().isBot()) {
                return;
            }

            // Respond using the correct ChatGPT instance according to the guild the message was sent into.
            String response = ChatCache.respond(event.getGuild().getId(), event.getAuthor(), event.getMessage().getContentRaw());
            event.getMessage().reply(response).complete();
        }
    }
}
