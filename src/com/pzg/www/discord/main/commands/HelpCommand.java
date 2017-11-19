package com.pzg.www.discord.main.commands;

import java.util.List;

import com.pzg.www.discord.object.Bot;
import com.pzg.www.discord.object.Command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.PermissionUtil;

public class HelpCommand implements Command {
	
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
	public Permission getPermissionNeeded() {
		return Permission.MESSAGE_WRITE;
	}
	
	@Override
	public boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args) {
		String helpMessage = "**__Commands:__**\n";
		for (Command command : bot.getCommands()) {
			if (!channel.getType().equals(ChannelType.PRIVATE)) {
				if (PermissionUtil.checkPermission(guild.getMember(user), command.getPermissionNeeded())) {
					helpMessage += "> " + command.getLabel() + "\n   - " + command.getDescription() + "\n";
				}
			} else {
				helpMessage = "Sorry, but you need to run this command on the server.";
			}
		}
		user.openPrivateChannel().complete().sendMessage(helpMessage).complete();
		return false;
	}
}