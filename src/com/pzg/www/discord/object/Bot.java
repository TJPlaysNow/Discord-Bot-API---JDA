package com.pzg.www.discord.object;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;

import com.pzg.www.discord.main.PluginMain;
import com.pzg.www.discord.object.logger.LogLevel;
import com.pzg.www.discord.object.logger.Logger;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * <b>The discord bot<b>
 * @version 1.0
 * @author TJPlaysNow
 */
public class Bot implements EventListener {
	
	private String prefix;
	private JDA jda;
	private List<Command> commands;
	
	private List<String> badWords;
	private List<User> mutedUsers;
	
	private boolean isOnline;
	
	private BotConsoleCommands consoleCommands;
	private BotThread botThread;
	
	private Runnable loadAction = null;
	
	private boolean isPlugin = false;
	
	private Logger logger;
	
	/**
	 * Create a new bot.
	 * @param token Verification token recieved from <a href="https://discordapp.com/developers/applications/me">Discord</a>
	 * @param prefix The command prefix.
	 */
	public Bot(String token, String prefix) {
		if (Bukkit.getServer() != null) {
			isPlugin = true;
		}
		
		logger = new Logger(LogLevel.INFO);
		
		this.prefix = prefix;
		commands = new ArrayList<Command>();
		badWords = new ArrayList<String>();
		mutedUsers = new ArrayList<User>();
		
		if (loadAction == null) {
			loadAction = () -> {};
		}
		
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(this).buildBlocking();
			isOnline = true;
		} catch (LoginException | IllegalArgumentException | InterruptedException e) {
			e.printStackTrace();
			isOnline = false;
		}
		
		
		consoleCommands = new BotConsoleCommands(this, isPlugin);
		if (isPlugin) {
			botThread = new BotThread(this, PluginMain.getPlugin(PluginMain.class));
		} else {
			botThread = new BotThread(this);
		}
	}
	
	/**
	 * Create a new bot.
	 * @param token Verification token recieved from <a href="https://discordapp.com/developers/applications/me">Discord</a>
	 * @param prefix The command prefix.
	 * @param logLevel The Log Level for the Bot's logger.
	 */
	public Bot(String token, String prefix, LogLevel logLevel) {
		if (Bukkit.getServer() != null) {
			isPlugin = true;
		}
		
		logger = new Logger(logLevel);
		
		this.prefix = prefix;
		commands = new ArrayList<Command>();
		badWords = new ArrayList<String>();
		mutedUsers = new ArrayList<User>();
		
		if (loadAction == null) {
			loadAction = () -> {};
		}
		
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(this).buildBlocking();
			isOnline = true;
		} catch (LoginException | IllegalArgumentException | InterruptedException e) {
			e.printStackTrace();
			isOnline = false;
		}
		
		
		consoleCommands = new BotConsoleCommands(this, isPlugin);
		if (isPlugin) {
			botThread = new BotThread(this, PluginMain.getPlugin(PluginMain.class));
		} else {
			botThread = new BotThread(this);
		}
	}
	
	/**
	 * This is used for indepth manipulation with the bot.
	 * @return JDA instance of the bot.
	 */
	public JDA getBot() {
		return jda;
	}
	
	/**
	 * Add a command to the bot.
	 * @param commad A new command.
	 */
	public void addCommand(Command commad) {
		commands.add(commad);
	}
	
	/**
	 * Get the bots commands.
	 * @return List of Command(s)
	 */
	public List<Command> getCommands() {
		return commands;
	}
	
	/**
	 * Add a bad word, and when a user uses the word it will be deleted and they will be warned.
	 * @param badWord
	 */
	public void addBadWord(String badWord) {
		badWords.add(badWord);
	}
	
	/**
	 * Mute a user easily by adding them to the muted users list.
	 * @param mutedUser
	 */
	public void addMutedUser(User mutedUser) {
		mutedUsers.add(mutedUser);
	}
	
	/**
	 * Unmute a user by removing them from the muted users list.
	 * @param unmutedUser
	 */
	public void removeMutedUser(User unmutedUser) {
		mutedUsers.remove(unmutedUser);
	}
	
	/**
	 * Checks to see if the bot is online.
	 * @return Boolean of online.
	 */
	public boolean isOnline() {
		return isOnline;
	}
	
	/**
	 * Stops the bot and goes offline.
	 */
	public void logout() {
		jda.shutdown();
		isOnline = false;
		
		
	}
	
	/**
	 * Add a new console command to the bot.<br>
	 * <i>Console commands mainly used for non-spigot.</i>
	 * @param command The new command to add.
	 */
	public void addConsoleCommand(ConsoleCommand command) {
		consoleCommands.addCommand(command);
	}
	
	/**
	 * Get the bots console commands.
	 * @return List of Console Command(s)
	 */
	public List<ConsoleCommand> getConsoleCommands() {
		return consoleCommands.getCommands();
	}
	
	/**
	 * A new action to run later.
	 * @param action The action to be run.
	 * @param seconds The time in seconds to wait before running the action.
	 */
	public void addAction(Runnable action, int seconds) {
		botThread.addAction(action, seconds);
	}
	
	/**
	 * Action to use on Bot loadup.
	 * @param loadAction The action to be run.
	 */
	public void setLoadAction(Runnable loadAction) {
		this.loadAction = loadAction;
	}
	
	/**
	 * Set the bot's playing game text.
	 * @param game Playing text.
	 */
	public void setGame(String game) {
		jda.getPresence().setGame(Game.playing(game));
	}
	
	/**
	 * Set the bot's playing game text, with a streaming url.
	 * @param game Playing text.
	 * @param url Streaming to <i>Twitch verified</i>.
	 */
	public void setGame(String game, String url) {
		if (Game.isValidStreamingUrl(url)) {
			jda.getPresence().setGame(Game.streaming(game, url));
		} else {
			jda.getPresence().setGame(Game.playing(game));
		}
	}
	
	/**
	 * <b>DO NOT CALL THIS METHOD</b><br><br>
	 * <i>This method is used directly by <b>Bot</b>.</i>
	 */
	public void consoleCheck() {
		consoleCommands.run();
	}
	
	/**
	 * <b>DO NOT CALL THIS METHOD</b><br><br>
	 * <i>This method it used directly by <b>JDA</b>.</i>
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof ReadyEvent) {
			loadAction.run();
		}
		if (e instanceof MessageReceivedEvent) {
			MessageReceivedEvent event = (MessageReceivedEvent) e;
			for (String badWord : badWords) {
				if (event.getMessage().getContentRaw().contains(badWord) || event.getMessage().getContentDisplay().contains(badWord) || event.getMessage().getContentStripped().contains(badWord)) {
					event.getMessage().delete().complete();
					Message message = event.getChannel().sendMessage("Watch your language " + event.getAuthor().getAsMention() + "!!").complete();
					botThread.addAction(() -> {
						message.delete().complete();
					}, 5);
				}
			}
			for (User user : mutedUsers) {
				if (user.getId().equals(event.getAuthor().getId())) {
					if (PermissionUtil.checkPermission(event.getMember(), Permission.ADMINISTRATOR)) {
						event.getChannel().sendMessage("You are muted, but you're an admin so oh well.").complete();
					} else {
						event.getMessage().delete().complete();
						Message message = event.getChannel().sendMessage("You are muted " + user.getAsMention() + ".").complete();
						botThread.addAction(() -> {
							message.delete().complete();
						}, 5);
					}
				}
			}
			for (Command command : commands) {
				if (event.getMessage().getContentRaw().startsWith(prefix)) {
					Message message = event.getMessage();
					User sender = event.getAuthor();
					MessageChannel channel = event.getChannel();
					Guild guild = event.getGuild();
					
					String[] commandArgs = message.getContentRaw().substring(prefix.length()).split(" ");
					
					List<String> args = new ArrayList<String>();
					
					for (String s : commandArgs) {
						args.add(s);
					}
					args.remove(0);
					
					if (commandArgs[0].equalsIgnoreCase(command.getLabel())) {
						if (PermissionUtil.checkPermission(event.getMember(), command.getPermissionNeeded())) {
							System.out.println("Running command " + command.getLabel());
							command.run(sender, channel, guild, commandArgs[0], args);
						}
					}
//					TODO: Fix the errors recieved by deleting the message.
//					if (!channel.getType().equals(ChannelType.PRIVATE)) {
//						addAction(() -> {
//							event.getChannel().getMessageById(event.getMessageId()).complete().delete().complete();
//						}, 5);
//					}
				}
			}
		}
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public Logger logger() {
		return logger;
	}
}