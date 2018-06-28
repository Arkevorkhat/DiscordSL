package botCore;

import java.util.ArrayList;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

public class Engine {
	public static final String BOT_VERSION = "1.0.0";
	public static ArrayList<Item> ItemStorage;
	public static FileHandler mainFileHandler;

	public static IDiscordClient botClient;
	public static EventDispatcher botClientDispatcher;
	
	public static boolean isInEchoMode = true;
	public static boolean isInVerboseMode = true;

	public static IDiscordClient createClient(String token, Boolean login) {
		ClientBuilder CB = new ClientBuilder();
		CB.withToken(token);
		try {
			if (login) {
				return CB.login();
			} else {
				return CB.build();
			}
		} catch (DiscordException e) {
			System.err.println(e.getErrorMessage());
			return null;
		}
	}

	/*
	 * if (args[0].equals("-s")) {
	 * Preferences.systemRoot().putBoolean("ShowDefaultLocationWarning", false);
	 * System.out.println("Warnings disabled."); } else if (args[0].equals("-v")) {
	 * Preferences.systemRoot().putBoolean("ShowDefaultLocationWarning", true); }
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("This application requires arguments to run correctly, Documentation:");
			StringGenHelper.printDocumentation();
			System.exit(1);
		} else if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-t":
					botClient = createClient(args[i + 1], false);
					botClientDispatcher = botClient.getDispatcher();
					botClientDispatcher.registerListener(new botCore.MessageListener());
					botClient.login();
					break;
				}
			}
			mainFileHandler = new FileHandler();
		}
		//boolean exit = false;
		//Scanner S = new Scanner(System.in);
		//while (!exit) {
		//	try {
		//		Thread.sleep(1000);
		//		System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_TIME));
		//		if (S.hasNextLine()) {
		//			exit = true;
		//		}
		//	} catch (InterruptedException e) {
		//		// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
		//}
		//S.close();
	}
}
