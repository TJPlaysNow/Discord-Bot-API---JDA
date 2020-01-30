package com.tjplaysnow.discord.main.consolecommands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommandConsole;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class BroadcastConsoleCommand extends ProgramCommandConsole {

	private Bot bot;
	
	public BroadcastConsoleCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public void run(String[] args) {
		for (Guild guild : bot.getBot().getGuilds()) {
			for (TextChannel channel : guild.getTextChannels()) {
				if (channel.getName().equalsIgnoreCase("announcements")) {
					String message = "";
					for (String s : args) {
						if (s.equalsIgnoreCase("broadcast")) {
							continue;
						}
						message += s + " ";
					}
					channel.sendMessage(message).complete();
					System.out.println("Sent the message.");
					System.out.println();
				}
			}
		}
	}
	
	@Override
	public String getLabel() {
		return "broadcast";
	}
	
	@Override
	public String getDescription() {
		return "Broadcasts a message to a channel in every guild.";
	}

	@Override
	public String getUsage() {
		return "broadcast <Message...>";
	}
}