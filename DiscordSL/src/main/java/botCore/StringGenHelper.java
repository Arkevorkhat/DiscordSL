package botCore;

import variableStores.Properties;

public class StringGenHelper {
	public static void printDocumentation() {
		System.out.print("Commands: \n");
		System.out.print("    -s : Silent mode, disables most warnings, not recommended.\n");
		System.out.print("    -v : Verbose mode, enables most warnings, recommended (Default)\n");
		System.out.print("    -t : Token, must be followed by a bot token.\n");
		System.out.print("    -echo : Echo mode, will take in a user's entry, and echo it back to the requester. (Default)\n");
		System.out.print("    -parse : Parsing mode, will parse user entry into a standard format. NYI\n");
	}
	public static String formatHelpFile() {
		StringBuilder SB = new StringBuilder();
		String prefix = Properties.getCommandPrefix();
		SB.append(prefix+"test : Shows a test message. \n");
		SB.append(prefix+"help : Shows this message.\n");
		SB.append(prefix+"add  : Adds a line to the shopping list.\n");
		SB.append(prefix+"pref : sets a preference, must be followed by the preference name, and then the expected value.\n");	
		SB.append(prefix+"exit : forcefully closes the bot.");
		return SB.toString();
	}
}
