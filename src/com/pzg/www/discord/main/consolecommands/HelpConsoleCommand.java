package com.pzg.www.discord.main.consolecommands;

import com.pzg.www.discord.object.Bot;
import com.pzg.www.discord.object.ConsoleCommand;

public class HelpConsoleCommand implements ConsoleCommand {
	
	private Bot bot;
	
	public HelpConsoleCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public void run(String[] args) {
		System.out.println(" - Help -");
		for (ConsoleCommand command : bot.getConsoleCommands()) {
			System.out.println("Command: " + command.getLabel());
			System.out.println(" - Description: " + command.getDescrition());
			System.out.println(" - Usage: " + command.getUsage());
		}
		System.out.println("");
	}
	
	@Override
	public String getLabel() {
		return "help";
	}
	
	@Override
	public String getDescrition() {
		return "Returns a list of commands and there information.";
	}

	@Override
	public String getUsage() {
		return "help";
	}
}