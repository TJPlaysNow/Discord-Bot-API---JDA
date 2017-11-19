package com.pzg.www.discord.main.consolecommands;

import com.pzg.www.discord.object.Bot;
import com.pzg.www.discord.object.ConsoleCommand;

public class StopConsoleCommand implements ConsoleCommand {
	
	private Bot bot;
	
	public StopConsoleCommand(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public void run(String[] args) {
		System.out.println("Stopping the program.");
		System.out.println("");
		bot.logout();
	}
	
	@Override
	public String getLabel() {
		return "stop";
	}
	
	@Override
	public String getDescrition() {
		return "Stops the program properly.";
	}
}