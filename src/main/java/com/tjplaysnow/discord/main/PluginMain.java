package com.tjplaysnow.discord.main;

import com.tjplaysnow.discord.main.metrics.Spigot;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {
	@Override
	public void onEnable() {
		Spigot metrics = new Spigot(this, 15871);
	}
}