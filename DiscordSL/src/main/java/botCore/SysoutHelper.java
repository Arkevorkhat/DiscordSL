package botCore;

public class SysoutHelper {
	public static void printDocumentation() {
		System.out.print("Commands: \n");
		System.out.print("    -s : Silent mode, disables most warnings, not recommended.\n");
		System.out.print("    -v : Verbose mode, enables most warnings, recommended (Default)\n");
		System.out.print("    -t : Token, must be followed by a bot token.\n");
		System.out.print("    -echo : Echo mode, will take in a user's entry, and echo it back to the requester. (Default)\n");
		System.out.print("    -parse : Parsing mode, will parse user entry into a standard format. NYI\n");
	}
}
