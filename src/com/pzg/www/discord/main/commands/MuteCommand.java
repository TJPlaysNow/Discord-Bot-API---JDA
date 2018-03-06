package com.pzg.www.discord.main.commands;

import java.util.List;

import com.pzg.www.discord.object.Bot;
import com.pzg.www.discord.object.Command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class MuteCommand implements Command {
	
	private final Bot bot;
	
	public MuteCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public String getLabel() {
		return "Mute";
	}
	
	@Override
	public String getDescription() {
		return "Mute a user. `!Mute [User]`";
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
					bot.addMutedUser(member.getUser());
					channel.sendMessage("Muted " + member.getAsMention() + ".").queue(delete());
					return true;
				}
			}
			if (!worked) {
				channel.sendMessage("Can't mute that person as they weren't found. Try `!Mute [User]`").queue(delete());
			}
		} else {
			channel.sendMessage("Can't mute that person as the args weren't right. Try `!Mute [User]`").queue(delete());
		}
		return false;
	}
}