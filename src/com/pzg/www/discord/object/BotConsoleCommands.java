package com.pzg.www.discord.object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

/**
 * <b>Console Command Class</b><br>
 * <i>Used dirctly by the <b>Bot</b>.</i>
 * @version 1.0
 * @author TJPlaysNow
 */
public class BotConsoleCommands implements Runnable {
	
	private List<ConsoleCommand> commands;
	private boolean isPlugin;
	private Thread thread;
	private boolean isRunning;
	private Scanner scanner;
	
	/**
	 * Create a new console command manager.
	 */
	public BotConsoleCommands(boolean isPlugin) {
		this.isPlugin = isPlugin;
		commands = new ArrayList<ConsoleCommand>();
		if (!isPlugin) {
			isRunning = true;
			thread = new Thread(this, "BotConsoleCommands");
			thread.start();
		}
	}
	
	/**
	 * Add a command to the manager.
	 * @param commad A new command.
	 */
	public void addCommand(ConsoleCommand command) {
		commands.add(command);
		if (isPlugin) {
			try {
				final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
				bukkitCommandMap.setAccessible(true);
				CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
				commandMap.register(command.getLabel(), new BotConsoleCommandBukkit(command));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the managers commands.
	 * @return List of Command(s)
	 */
	public List<ConsoleCommand> getCommands() {
		return commands;
	}
	
	/**
	 * Stops the Bot Console Command thread.
	 */
	public void stop() {
		thread.interrupt();
		isRunning = false;
	}
	
	/**
	 * <b>DO NOT CALL THIS METHOD</b><br><br>
	 * <i>This method is constantly being looped<br>
	 * to test if the next command ran is a <br>
	 * <b>Console Command</b> and will run it if so.</i>
	 */
	public void run() {
		while (isRunning) {
			scanner = new Scanner(System.in);
			String[] args = scanner.nextLine().split(" ");
			for (ConsoleCommand command : commands) {
				if (args[0].equalsIgnoreCase(command.getLabel())) {
					command.run(args);
				}
			}
		}
		scanner.close();
	}
}