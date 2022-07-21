package com.tjplaysnow.discord.main.commands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Collections;
import java.util.List;

public class UnmuteCommand extends ProgramCommand {
	
	private final Bot bot;
	
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
	public List<OptionData> getOptionData() {
		return Collections.singletonList(new OptionData(OptionType.USER, "user", "The user to mute.", true));
	}
	
	@Override
	public boolean run(SlashCommandInteractionEvent event) {
		Member member = event.getOption("user").getAsMember();
		if (member != null) {
			bot.removeMutedUser(member.getUser());
			event.getHook().sendMessage("Unmuted " + member.getAsMention() + ".").queue(delete());
			return true;
		}
		event.getHook().sendMessage("Can't unmute that person as they weren't found. Try `!Unmute [User]`").queue(delete());
		return false;
	}
}