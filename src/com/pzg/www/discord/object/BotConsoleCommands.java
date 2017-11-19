package com.pzg.www.discord.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <b>Console Command Class</b><br>
 * <i>Used dirctly by the <b>Bot</b>.</i>
 * @version 1.0
 * @author TJPlaysNow
 */
public class BotConsoleCommands implements Runnable {
	
	private Bot bot;
	
	private List<ConsoleCommand> commands;
	private Scanner scanner;
	
	/**
	 * Create a new console command manager.
	 * @param bot The bot that is creating the manager.
	 */
	public BotConsoleCommands(Bot bot) {
		this.bot = bot;
		commands = new ArrayList<ConsoleCommand>();
		new Thread(this).start();
		Thread.currentThread().setName("Console Command Manager");
	}
	
	/**
	 * Add a command to the manager.
	 * @param commad A new command.
	 */
	public void addCommand(ConsoleCommand command) {
		commands.add(command);
	}
	
	/**
	 * Get the managers commands.
	 * @return List of Command(s)
	 */
	public List<ConsoleCommand> getCommands() {
		return commands;
	}
	
	/**
	 * <b>DO NOT CALL THIS METHOD</b><br><br>
	 * <i>This method is constantly being looped<br>
	 * to test if the next command ran is a <br>
	 * <b>Console Command</b> and will run it if so.</i>
	 */
	@Override
	public void run() {
		while (bot.isOnline()) {
			scanner = new Scanner(System.in);
			String[] args = scanner.nextLine().split(" ");
			for (ConsoleCommand command : commands) {
				if (args[0].equalsIgnoreCase(command.getLabel())) {
					command.run(args);
				}
			}
		}
	}
}