package com.pzg.www.discord.main.commands;

import java.util.List;

import com.pzg.www.discord.object.Bot;
import com.pzg.www.discord.object.Command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class UnmuteCommand implements Command {
	
	private Bot bot;
	
	public UnmuteCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public String getLabel() {
		return "Unmute";
	}
	
	@Override
	public String getDescription() {
		return "Unmute a user. `!Unmute [User]`";
	}
	
	@Override
	public Permission getPermissionNeeded() {
		return Permission.MESSAGE_MANAGE;
	}
	
	@Override
	public boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args) {
		if (args.size() == 1) {
			boolean worked = false;
			for (Member member : guild.getMembers()) {
				if (member.getAsMention().equalsIgnoreCase(args.get(0))) {
					bot.removeMutedUser(member.getUser());
					channel.sendMessage("Unmuted " + member.getAsMention() + ".").complete();
					return true;
				}
			}
			if (!worked) {
				channel.sendMessage("Can't unmute that person as they weren't found. Try `!Unmute [User]`").complete();
			}
		} else {
			channel.sendMessage("Can't unmute that person as the args weren't right. Try `!Unmute [User]`").complete();
		}
		return false;
	}
}