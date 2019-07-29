package com.tjplaysnow.discord.main.consolecommands;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "discordbotapi", name = "Discord Bot API", url = "http://tjplaysnow.com/", authors = "TJPlaysNow", description = "This plugin is an API to use a Discord ProgramBot.", version = "1.0")
public class SpongeMain {
	@Listener
	public void onServerStart(GameStartedServerEvent event) {}
}