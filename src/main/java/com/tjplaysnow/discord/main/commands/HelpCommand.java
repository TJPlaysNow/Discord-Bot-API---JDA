package com.tjplaysnow.discord.main.commands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.List;
import java.util.Objects;

public class HelpCommand extends ProgramCommand {
	
	private Bot bot;
	
	public HelpCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public String getLabel() {
		return "Help";
	}
	
	@Override
	public String getDescription() {
		return "The command that tells all.";
	}
	
	@Override
	public boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args) {
		StringBuilder helpMessage = new StringBuilder("**__Commands:__**\n");
		for (ProgramCommand command : bot.getCommands()) {
			if (!channel.getType().equals(ChannelType.PRIVATE)) {
				if (PermissionUtil.checkPermission(Objects.requireNonNull(guild.getMember(user)), command.getPermissionNeeded())) {
					helpMessage.append("> ").append(command.getLabel()).append("\n   - ").append(command.getDescription()).append("\n");
				}
			} else {
				helpMessage = new StringBuilder("Sorry, but you need to run this command on the server.");
			}
		}
		
		
		user.openPrivateChannel().complete().sendMessage(helpMessage.toString()).complete();
		return false;
	}
}