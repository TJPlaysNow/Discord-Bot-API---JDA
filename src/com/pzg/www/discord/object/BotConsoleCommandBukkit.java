package com.pzg.www.discord.object;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class BotConsoleCommandBukkit extends BukkitCommand {
	
	ConsoleCommand command;
	
	public BotConsoleCommandBukkit(ConsoleCommand command) {
		super(command.getLabel());
		this.description = command.getDescrition();
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