package com.tjplaysnow.discord.main;

import com.tjplaysnow.discord.main.metrics.Bungee;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {
	@Override
	public void onEnable() {
		Bungee metrics = new Bungee(this, 15872);
	}
}