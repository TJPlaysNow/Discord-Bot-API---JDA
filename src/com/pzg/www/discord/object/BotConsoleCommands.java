package com.pzg.www.discord.object;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.pzg.www.discord.utils.InputUtils;

/**
 * <b>Console Command Class</b><br>
 * <i>Used dirctly by the <b>Bot</b>.</i>
 * @version 1.0
 * @author TJPlaysNow
 */
public class BotConsoleCommands {
	
	private List<ConsoleCommand> commands;
	
	/**
	 * Create a new console command manager.
	 * @param bot The bot that is creating the manager.
	 */
	public BotConsoleCommands() {
		commands = new ArrayList<ConsoleCommand>();
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
	public void run() {
		Closeable reader = InputUtils.scanLines(System.in, line -> {
			String[] args = line.split(" ");
			for (ConsoleCommand command : commands) {
				if (args[0].equalsIgnoreCase(command.getLabel())) {
					command.run(args);
				}
			}
		});
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}