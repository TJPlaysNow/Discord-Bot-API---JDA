package com.tjplaysnow.discord.main.consolecommands;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.ProgramCommandConsole;

public class StopConsoleCommand extends ProgramCommandConsole {
	
	private Bot bot;
	private Runnable runnable;
	
	public StopConsoleCommand(Bot bot) {
		this.bot = bot;
		runnable = () -> {
		};
	}
	
	public StopConsoleCommand(Runnable runnable, Bot bot) {
		this.bot = bot;
		this.runnable = runnable;
	}
	
	@Override
	public void run(String[] args) {
		System.out.println("Stopping the program.");
		System.out.println();
		runnable.run();
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