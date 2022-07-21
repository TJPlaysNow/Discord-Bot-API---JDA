package com.tjplaysnow.discord.object;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CommandBungeeManager extends ProgramConsoleCommandManager {
	
	private final List<ProgramCommandConsole> commands;
	private final Plugin plugin;
	
	protected CommandBungeeManager(Plugin plugin) {
		super(false);
		commands = new ArrayList<>();
		this.plugin = plugin;
	}
	
	@Override
	public void addCommand(ProgramCommandConsole command) {
		commands.add(command);
		ProxyServer.getInstance().getPluginManager().registerCommand(plugin, new Command(command.getLabel(), "op") {
			@Override
			public void execute(CommandSender commandSender, String[] strings) {
				command.run(strings);
			}
		});
	}
	
	@Override
	public List<ProgramCommandConsole> getCommands() {
		return commands;
	}
	
	@Override
	public void stop() {
	
	}
	
	@Override
	public void run() {
	
	}
}