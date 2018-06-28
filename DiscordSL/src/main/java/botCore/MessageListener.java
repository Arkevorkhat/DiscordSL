package botCore;

import java.util.ArrayList;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import variableStores.Properties;

public class MessageListener implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent arg0) {
		// try {
		if (arg0.getAuthor().getLongID() != Engine.botClient.getOurUser().getLongID()) {
			String tempContent = arg0.getMessage().getContent();
			if (tempContent.substring(0, 1).equals(Properties.getCommandPrefix())) {
				if (arg0.getAuthor().isBot() != true) {
					IChannel sentChannel = arg0.getChannel();
					System.out.println(arg0.getMessage());
					String[] tempContentSplit = tempContent.substring(1).split(Properties.getEntryDelimiter());
					String Command = tempContentSplit[0];
					String content = tempContent.replace(Properties.getCommandPrefix() + Command, "");
					System.out.println(content);
					System.out.println("Command: " + tempContent.substring(1));
					switch (Command.toLowerCase()) {
					case "test":
						sentChannel.sendMessage(
								"Well, thanks for the test message, I hope you enjoy the bot. to see other commmands, type "
										+ Properties.getCommandPrefix() + "help");
						break;
					case "pref":
						if (tempContentSplit.length == 3) {
							sentChannel.sendMessage(Properties.setPrefByName(tempContentSplit[1], tempContentSplit[2]));
						} else {
							sentChannel.sendMessage("Sorry, but I need two arguments for the command "
									+ Properties.commandPrefix + "pref.");
						}
						break;
					case "help":
						sentChannel.sendMessage(StringGenHelper.formatHelpFile());
						break;
					case "add":
						if (Engine.mainFileHandler.requestLists.containsKey(arg0.getGuild().getLongID())) {
							Engine.mainFileHandler.requestLists.get(arg0.getGuild().getLongID()).add(content);
						} else {
							Engine.mainFileHandler.requestLists.put(arg0.getGuild().getLongID(),
									new ArrayList<String>());
							Engine.mainFileHandler.requestLists.get(arg0.getGuild().getLongID()).add(content);
						}
						break;
					case "get":
						if (Engine.mainFileHandler.requestLists.containsKey(arg0.getGuild().getLongID())) {
							get(arg0);
						} else {
							Engine.mainFileHandler.importListByID(arg0.getGuild().getLongID());
							get(arg0);
							// sentChannel.sendMessage("There is no shopping list in storage for this
							// server.");
						}
						break;
					case "remove":
						try {
							int index = Integer.parseInt(content.trim());
							if (Engine.mainFileHandler.requestLists.containsKey(arg0.getGuild().getLongID())) {
								try {
									sentChannel.sendMessage("removed: " + Engine.mainFileHandler.requestLists
											.get(arg0.getGuild().getLongID()).remove(index));
								} catch (IndexOutOfBoundsException e) {
									sentChannel.sendMessage("That item doesn't exist!");
								}
							} else {
								sentChannel.sendMessage("You need to get the requests before you do that.");

							}
						} catch (NumberFormatException e) {
							sentChannel.sendMessage(
									"Hey now, you need to give me the number next to the item you want to remove!");
							e.printStackTrace();
						}
						break;
					case "source":
						sentChannel.sendMessage(
								"You can find my source code at https://www.github.com/Arkevorkhat/DiscordSL");
						break;
					case "relog":
						Engine.botClient.logout();
						Engine.botClient.login();
					case "exit":
						sentChannel.sendMessage("Okay.");
						Engine.mainFileHandler.exportLists();
						Engine.botClient.logout();
						System.exit(0);
					}
				}
			}
		}
		// } catch (NullPointerException e) {
		// arg0.getChannel().sendMessage("Something has gone wrong... unable to process
		// your request.");
		// }
	}

	public static void get(MessageReceivedEvent arg0) {
		StringBuilder SB = new StringBuilder();
		SB.append("\n");
		if (Engine.mainFileHandler.requestLists.get(arg0.getGuild().getLongID()).size() != 0) {
			for (String S : Engine.mainFileHandler.requestLists.get(arg0.getGuild().getLongID())) {
				SB.append(Engine.mainFileHandler.requestLists.get(arg0.getGuild().getLongID()).indexOf(S) + " " + S
						+ "\n");
			}
			arg0.getChannel().sendMessage(SB.toString());
		} else {
			arg0.getChannel().sendMessage("There are no items on the list.");
		}
	}
}