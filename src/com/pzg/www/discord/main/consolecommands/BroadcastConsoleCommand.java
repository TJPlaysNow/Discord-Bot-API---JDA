package com.pzg.www.discord.main.consolecommands;

import com.pzg.www.discord.object.Bot;
import com.pzg.www.discord.object.ConsoleCommand;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class BroadcastConsoleCommand implements ConsoleCommand {

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
					System.out.println("");
				}
			}
		}
	}
	
	@Override
	public String getLabel() {
		return "broadcast";
	}
	
	@Override
	public String getDescrition() {
		return "Broadcasts a message to a channel in every guild.";
	}
}