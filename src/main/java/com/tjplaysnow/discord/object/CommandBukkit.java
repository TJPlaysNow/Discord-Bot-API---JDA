package com.tjplaysnow.discord.object;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class CommandBukkit extends BukkitCommand {
	
	private ProgramCommandConsole command;
	
	public CommandBukkit(ProgramCommandConsole command) {
		super(command.getLabel());
		this.description = command.getDescription();
		this.usageMessage = "/" + command.getUsage();
		this.setPermission("console.command");
		this.command = command;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (!sender.hasPermission(this.getPermission())) {
			sender.sendMessage("§cUh oh, you don't have permission for that!");
			return true;
		}
		command.run(args);
		return true;
	}
}