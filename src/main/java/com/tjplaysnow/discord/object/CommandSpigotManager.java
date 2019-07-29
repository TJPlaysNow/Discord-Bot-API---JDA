package com.tjplaysnow.discord.object;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandSpigotManager extends ProgramConsoleCommandManager {
	
	private List<ProgramCommandConsole> commands;
	
	public CommandSpigotManager() {
		super(false);
		commands = new ArrayList<>();
	}
	
	@Override
	public void addCommand(ProgramCommandConsole command) {
		commands.add(command);
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			commandMap.register(command.getLabel(), new CommandBukkit(command));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<ProgramCommandConsole> getCommands() {
		return commands;
	}
	
	@Override
	public void stop() {}
	
	@Override
	public void run() {}
}