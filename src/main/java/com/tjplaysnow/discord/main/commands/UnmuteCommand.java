package com.tjplaysnow.discord.main.commands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class UnmuteCommand extends ProgramCommand {
	
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
			for (Member member : guild.getMembers()) {
				if (member.getAsMention().equalsIgnoreCase(args.get(0))) {
					bot.removeMutedUser(member.getUser());
					channel.sendMessage("Unmuted " + member.getAsMention() + ".").complete();
					return true;
				}
			}
			channel.sendMessage("Can't unmute that person as they weren't found. Try `!Unmute [User]`").complete();
		} else {
			channel.sendMessage("Can't unmute that person as the args weren't right. Try `!Unmute [User]`").complete();
		}
		return false;
	}
}