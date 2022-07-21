package com.tjplaysnow.discord.main.commands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HelpCommand extends ProgramCommand {
	
	private final Bot bot;
	
	public HelpCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public String getLabel() {
		return "help";
	}
	
	@Override
	public List<OptionData> getOptionData() {
		return null;
	}
	
	@Override
	public String getDescription() {
		return "The command that tells all.";
	}
	
	@Override
	public boolean run(@NotNull SlashCommandInteractionEvent event) {
		StringBuilder helpMessage = new StringBuilder("**__Commands:__**\n");
		for (ProgramCommand command : bot.getCommands()) {
			if (!event.getChannel().getType().equals(ChannelType.PRIVATE)) {
				if (event.getMember() != null) {
					if (PermissionUtil.checkPermission(event.getMember(), command.getPermissionNeeded())) {
						helpMessage.append("> ").append(command.getLabel()).append("\n   - ").append(command.getDescription()).append("\n");
					}
				} else {
					helpMessage = new StringBuilder("Sorry, but your member object is null. (Because you are OP possibly)");
				}
			} else {
				helpMessage = new StringBuilder("Sorry, but you need to run this command on the server.");
			}
		}
		event.getHook().sendMessage(helpMessage.toString()).queue();
		return false;
	}
}