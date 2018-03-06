package com.pzg.www.discord.main;

import com.pzg.www.discord.main.commands.HelpCommand;
import com.pzg.www.discord.main.commands.MuteCommand;
import com.pzg.www.discord.main.commands.UnmuteCommand;
import com.pzg.www.discord.main.consolecommands.StopConsoleCommand;
import com.pzg.www.discord.object.Bot;

public class Main {
	public static void main(String[] args) {
//		TODO: Nothing.
		Bot bot = new Bot("Mzc4MzY0NjY3MzUwNzQ1MDg4.DX-PkQ.G-R-QYK2s1JYealZDJDBdxiyzO4", "!");
		bot.addCommand(new MuteCommand(bot));
		bot.addCommand(new UnmuteCommand(bot));
		bot.addCommand(new HelpCommand(bot));
		bot.addConsoleCommand(new StopConsoleCommand(bot));
	}
}