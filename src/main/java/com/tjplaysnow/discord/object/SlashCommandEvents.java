package com.tjplaysnow.discord.object;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandEvents extends ListenerAdapter {
	
	private final Bot bot;
	
	public SlashCommandEvents(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public void onSlashCommand(@NotNull SlashCommandEvent event) {
		for (ProgramCommand command : bot.getCommands()) {
			if (event.getName().equals(command.getLabel())) {
				event.deferReply().queue();
				command.run(event);
			}
		}
	}
}
