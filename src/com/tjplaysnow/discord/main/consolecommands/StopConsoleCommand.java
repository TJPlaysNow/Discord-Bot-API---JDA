package com.tjplaysnow.discord.main.consolecommands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommandConsole;

public class StopConsoleCommand extends ProgramCommandConsole {
	
	private Bot bot;
	
	public StopConsoleCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public void run(String[] args) {
		System.out.println("Stopping the program.");
		System.out.println();
		bot.logout();
	}
	
	@Override
	public String getLabel() {
		return "stop";
	}
	
	@Override
	public String getDescription() {
		return "Stops the program properly.";
	}

	@Override
	public String getUsage() {
		return "stop";
	}
}