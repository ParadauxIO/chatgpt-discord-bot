package io.paradaux.bot.listeners;

import io.paradaux.bot.ListenerUtils;
import io.paradaux.openai.ChatCache;
import io.paradaux.util.ConfigHandler;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class RandomResponseListener implements EventListener {

    private static final ConfigHandler.Config config = ConfigHandler.getConfig();
    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if (genericEvent instanceof MessageReceivedEvent event) {
            // Return if random number is below the random response chance.
            if (Math.random() > config.bot().randomResponseChance()) {
                return;
            }

            // If in a listening channel
            if (config.bot().listeningChannels().contains(event.getChannel().getId())) {
                return;
            }

            // Don't respond to yourself or other bots!
            if (event.getAuthor().isBot()) {
                return;
            }

            // Other disable conditions
            if (ListenerUtils.isDisabledHere(event.getChannel().getId(), event.getGuild().getId())) {
                return;
            }

            // Respond using the correct ChatGPT instance according to the guild the message was sent into.
            String response = ChatCache.respond(event.getMessage());
            event.getMessage().reply(response).queue();
        }
    }
}
