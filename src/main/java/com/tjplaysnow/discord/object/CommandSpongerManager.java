package com.tjplaysnow.discord.object;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.util.List;

public class CommandSpongerManager extends ProgramConsoleCommandManager {
	
	private Plugin plugin;
	
	protected CommandSpongerManager(Plugin plugin) {
		super(false);
		this.plugin = plugin;
	}
	
	@Override
	public void addCommand(ProgramCommandConsole command) {
		CommandSpec myCommandSpec = CommandSpec.builder()
				                            .description(Text.of(command.getDescription()))
				                            .permission("discord.operator")
				                            .executor((sender, args) -> {
				                                command.run(args.<String>getAll("").toArray(new String[]{}));
				                                return CommandResult.builder().successCount(1).build();
				                            })
				                            .build();
		
		Sponge.getCommandManager().register(plugin, myCommandSpec, "helloworld", "hello", "test");
	}
	
	@Override
	public List<ProgramCommandConsole> getCommands() {
		return null;
	}
	
	@Override
	public void stop() {
	
	}
	
	@Override
	public void run() {
	
	}
}