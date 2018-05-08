package com.tjplaysnow.discord.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandConsoleManager extends ProgramConsoleCommandManager {
	
	private List<ProgramCommandConsole> commands;
	private Scanner scanner;
	
	public CommandConsoleManager() {
		super(true);
		commands = new ArrayList<>();
	}
	
	@Override
	public void addCommand(ProgramCommandConsole command) {
		commands.add(command);
	}
	
	@Override
	public List<ProgramCommandConsole> getCommands() {
		return commands;
	}
	
	@Override
	public void stop() {
		stopThread();
	}
	
	@Override
	public void run() {
		while (isRunning()) {
			scanner = new Scanner(System.in);
			String[] args = scanner.nextLine().split(" ");
			for (ProgramCommandConsole command : commands) {
				if (args[0].equalsIgnoreCase(command.getLabel())) {
					command.run(args);
				}
			}
		}
		scanner.close();
	}
}