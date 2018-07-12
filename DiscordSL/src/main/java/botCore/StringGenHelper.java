package botCore;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import variableStores.Properties;

public class StringGenHelper {
	public static void printDocumentation() {
		System.out.print("Commands: \n");
		System.out.print("    -s : Silent mode, disables most warnings, not recommended.\n");
		System.out.print("    -v : Verbose mode, enables most warnings, recommended (Default)\n");
		System.out.print("    -t : Token, must be followed by a bot token.\n");
		System.out.print(
				"    -echo : Echo mode, will take in a user's entry, and echo it back to the requester. (Default)\n");
		System.out.print("    -parse : Parsing mode, will parse user entry into a standard format. NYI\n");
	}

	public static String formatHelpFile() {
		StringBuilder SB = new StringBuilder();
		String prefix = Properties.getCommandPrefix();
		SB.append(prefix + "test   : Shows a test message. \n");
		SB.append(prefix + "help   : Shows this message.\n");
		SB.append(prefix + "add    : Adds a line to the shopping list.\n");
		SB.append(prefix + "get    : shows the current list of items. \n");
		SB.append(prefix + "remove : removes a line from the shopping list. \n");
		SB.append(prefix
				+ "pref   : sets a preference, must be followed by the preference name, and then the expected value. (Requires admin permissions.)\n");
		SB.append(prefix
				+ "reply  : Sets the channel that this command is issued in as the reply channel. (requires admin permissions.)\n");
		SB.append(prefix + "exit   : forcefully closes the bot. (Requires admin permissions.)");
		return SB.toString();
	}

	public static EmbedObject embedHelpFile() {
		EmbedBuilder EB = new EmbedBuilder();
		EB.appendField("test", "Shows a test message", false);
		EB.appendField("help","Shows this message", false);
		EB.appendField("add", "Adds a line to the shopping list", false);
		EB.appendField("remove","Removes the line with the given number from the shopping list.", false);
		EB.appendField("get", "Prints the current contents of the shopping list.", false);
		//EB.appendField("pref", "Sets a preference, must be followed by a preference name, and then a desired value.",false);
		return EB.build();
	}
}
