package io.paradaux.bot.listeners;

import io.paradaux.bot.ListenerUtils;
import io.paradaux.openai.ChatCache;
import io.paradaux.util.ConfigHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MentionListener implements EventListener {

    private static final ConfigHandler.Config config = ConfigHandler.getConfig();

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if (genericEvent instanceof MessageReceivedEvent event) {
            if (!config.bot().respondOnMention()) {
                return;
            }

            // If in a listening channel
            if (config.bot().listeningChannels().contains(event.getChannel().getId())) {
                return;
            }

            // Other disable conditions
            if (ListenerUtils.isDisabledHere(event.getChannel().getId(), event.getGuild().getId())) {
                return;
            }

            List<Member> mentionedMembers = event.getMessage().getMentions().getMembers();
            if (mentionedMembers.contains(event.getGuild().getSelfMember())) {
                String response = ChatCache.respond(event.getMessage());
                event.getMessage().reply(response).queue();
            }
        }
    }
}
