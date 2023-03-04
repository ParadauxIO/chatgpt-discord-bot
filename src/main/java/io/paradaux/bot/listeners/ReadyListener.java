package io.paradaux.bot.listeners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class ReadyListener implements EventListener {
    private static final String READY_MESSAGE = """
            *-----------------*
            |  Friendly Bot   |
            | ChatGPT Edition |
            *-----------------*
            | Guilds: %3d     |
            *-----------------*%n
            """;



    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if (genericEvent instanceof ReadyEvent event) {
            System.out.printf(READY_MESSAGE, event.getGuildAvailableCount());
        }
    }
}
