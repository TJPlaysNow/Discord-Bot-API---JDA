package com.tjplaysnow.discord.main.consolecommands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommandConsole;

public class HelpConsoleCommand extends ProgramCommandConsole {
	
	private Bot bot;
	
	public HelpConsoleCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public void run(String[] args) {
		System.out.println(" - Help -");
		for (ProgramCommandConsole command : bot.getConsoleCommands()) {
			System.out.println("ProgramCommand: " + command.getLabel());
			System.out.println(" - Description: " + command.getDescription());
			System.out.println(" - Usage: " + command.getUsage());
		}
		System.out.println();
	}
	
	@Override
	public String getLabel() {
		return "help";
	}
	
	@Override
	public String getDescription() {
		return "Returns a list of commands and there information.";
	}

	@Override
	public String getUsage() {
		return "help";
	}
}