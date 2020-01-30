package com.tjplaysnow.discord.main.commands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class MuteCommand extends ProgramCommand {
	
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
			for (Member member : guild.getMembers()) {
				if (member.getAsMention().equalsIgnoreCase(args.get(0))) {
					bot.addMutedUser(member.getUser());
					channel.sendMessage("Muted " + member.getAsMention() + ".").queue(delete());
					return true;
				}
			}
			channel.sendMessage("Can't mute that person as they weren't found. Try `!Mute [User]`").queue(delete());
		} else {
			channel.sendMessage("Can't mute that person as the args weren't right. Try `!Mute [User]`").queue(delete());
		}
		return false;
	}
}