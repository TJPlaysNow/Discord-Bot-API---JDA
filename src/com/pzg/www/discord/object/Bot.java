package com.pzg.www.discord.object;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

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
import net.dv8tion.jda.core.exceptions.RateLimitedException;
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
	
	private Action loadAction;
	
	/**
	 * Create a new bot.
	 * @param token Verification token recieved from <a href="https://discordapp.com/developers/applications/me">Discord</a>
	 * @param prefix The command prefix.
	 */
	public Bot(String token, String prefix) {
		this.prefix = prefix;
		commands = new ArrayList<Command>();
		badWords = new ArrayList<String>();
		mutedUsers = new ArrayList<User>();
		loadAction = new Action() {@Override public void run() {}};
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(this).buildBlocking();
			isOnline = true;
		} catch (LoginException | IllegalArgumentException | RateLimitedException | InterruptedException e) {
			e.printStackTrace();
			isOnline = false;
		}
		consoleCommands = new BotConsoleCommands(this);
		botThread = new BotThread(this);
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
	public void addAction(Action action, int seconds) {
		botThread.addAction(action, seconds);
	}
	
	/**
	 * Action to use on Bot loadup.
	 * @param loadAction The action to be run.
	 */
	public void setLoadAction(Action loadAction) {
		this.loadAction = loadAction;
	}
	
	/**
	 * Set the bot's playing game text.
	 * @param game Playing text.
	 */
	public void setGame(String game) {
		jda.getPresence().setGame(Game.of(game));
	}
	
	/**
	 * Set the bot's playing game text, with a streaming url.
	 * @param game Playing text.
	 * @param url Streaming to <i>Twitch verified</i>.
	 */
	public void setGame(String game, String url) {
		if (Game.isValidStreamingUrl(url)) {
			jda.getPresence().setGame(Game.of(game, url));
		} else {
			jda.getPresence().setGame(Game.of(game));
		}
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
				if (event.getMessage().getContent().contains(badWord)) {
					event.getMessage().delete().complete();
					Message message = event.getChannel().sendMessage("Watch your language " + event.getAuthor().getAsMention() + "!!").complete();
					botThread.addAction(new Action() {
						@Override
						public void run() {
							message.delete().complete();
						}
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
						botThread.addAction(new Action() {
							@Override
							public void run() {
								message.delete().complete();
							}
						}, 5);
					}
				}
			}
			for (Command command : commands) {
				if (event.getMessage().getContent().startsWith(prefix)) {
					Message message = event.getMessage();
					User sender = event.getAuthor();
					MessageChannel channel = event.getChannel();
					Guild guild = event.getGuild();
					
					String[] commandArgs = message.getContent().substring(prefix.length()).split(" ");
					
					List<String> args = new ArrayList<String>();
					
					for (String s : commandArgs) {
						args.add(s);
					}
					args.remove(0);
					
					if (commandArgs[0].equalsIgnoreCase(command.getLabel())) {
						if (PermissionUtil.checkPermission(event.getMember(), command.getPermissionNeeded())) {
							command.run(sender, channel, guild, commandArgs[0], args);
						}
					}
//					TODO: Fix the errors recieved by deleting the message.
//					if (!channel.getType().equals(ChannelType.PRIVATE)) {
//						event.getMessage().delete().complete();
//					}
				}
			}
		}
	}
	
}