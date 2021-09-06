package com.tjplaysnow.discord.main.commands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Collections;
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
	public List<OptionData> getOptionData() {
		return Collections.singletonList(new OptionData(OptionType.USER, "user", "The user to mute.", true));
	}
	
	@Override
	public boolean run(SlashCommandEvent event) {
		Member member = event.getOption("user").getAsMember();
		if (member != null) {
			bot.addMutedUser(member.getUser());
			event.getHook().sendMessage("Muted " + member.getAsMention() + ".").queue(delete());
			return true;
		}
		event.getHook().sendMessage("Can't mute that person as they weren't found. Try `!Mute [User]`").queue(delete());
		return false;
	}
}