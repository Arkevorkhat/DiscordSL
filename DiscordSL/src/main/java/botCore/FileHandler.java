package botCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

import constantStores.FileConst;
import sx.blah.discord.handle.obj.IGuild;

public class FileHandler {
	public File mainStorageDir = variableStores.Properties.getCoreDirectory();
	public File itemsFile;
	public File requestsFile;
	public File filesList;
	public final File prefsFile;

	public HashMap<Long, ArrayList<String>> requestLists = new HashMap<Long, ArrayList<String>>();
	File Servers = new File(mainStorageDir.getAbsolutePath() + File.separatorChar + "servers" + File.separatorChar);

	public FileHandler() {
		switch (System.getProperty("os.name")) {
		case "Windows":
			mainStorageDir = FileConst.winFile;
			break;
		case "Mac":
			mainStorageDir = FileConst.macFile;
			break;
		case "Linux":
			mainStorageDir = FileConst.linFile;
			break;
		case "Solaris":
			System.err.println(
					"Solaris is not supported in this version of the bot, please use a different operating system.");
			break;
		}
		System.out.println(mainStorageDir.mkdirs() + mainStorageDir.getAbsolutePath());
		itemsFile = new File(mainStorageDir.getAbsolutePath() + "//Items.bin");
		requestsFile = new File(mainStorageDir.getAbsolutePath() + "//Requests.bin");
		prefsFile = new File(mainStorageDir.getAbsolutePath() + "//Bot.properties");
		filesList = new File(mainStorageDir.getAbsolutePath() + "//lists.nlsv"); // nlsv is NewLine Separated Values
		try {
			System.out.println(itemsFile.createNewFile());
			System.out.println(prefsFile.createNewFile());
			System.out.println(requestsFile.createNewFile());
		} catch (IOException e) {
			if (e.getMessage().contains("Exists")) {
				System.out.println("File already exists, Skipping.");
			} else
				System.out.println(e.getMessage());
		}
	}

	public void setDirs() {

	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public void loadLists() {
		try {
			FileInputStream FIS = new FileInputStream(filesList);
			Scanner fileScan = new Scanner(FIS);
			while (fileScan.hasNextLong()) {
				Long inter = fileScan.nextLong();
				File interim = new File(mainStorageDir.getAbsolutePath() + "//" + inter + ".bin");
				ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(interim));
				requestLists.put(inter, (ArrayList<String>) OIS.readObject());
				OIS.close();
			}
			FIS.close();
			fileScan.close();
		} catch (FileNotFoundException e) {
			try {
				filesList.createNewFile();
			} catch (IOException e1) {
				System.err.println("Failed to load files, bot functionality will not work as expected.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public void loadPrefs() {
		Preferences UsrPrefs = Preferences.userRoot();
		variableStores.Properties.setCommandPrefix(UsrPrefs.get("commandPrefix", "&"));
		variableStores.Properties.setEntryDelimiter(UsrPrefs.get("entryDelimiter", ";"));
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public ArrayList<Item> getItems() {
		try {
			ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(itemsFile));
			ArrayList<Item> IAL = (ArrayList<Item>) OIS.readObject();
			OIS.close();
			return IAL;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			this.setDirs();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Deprecated
	public ArrayList<String> getRequests() {
		try {
			ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(requestsFile));
			@SuppressWarnings("unchecked")
			ArrayList<String> SAL = (ArrayList<String>) OIS.readObject();
			OIS.close();
			return SAL;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Deprecated
	public void exportLists() {
		List<IGuild> guilds = Engine.botClient.getGuilds();
		for (IGuild IG : guilds) {
			try {
				File f = new File(mainStorageDir.getAbsolutePath() + "//" + IG.getLongID() + ".bin");
				f.createNewFile();
				ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(f));
				OOS.writeObject(requestLists.get(IG.getLongID()));
				OOS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void exportPrefs() {
		List<ServerPref<String>> prefs = new ArrayList<ServerPref<String>>(Engine.Servers.values());
		File Servers = new File(mainStorageDir.getAbsolutePath() + File.separatorChar + "servers");
		Servers.mkdirs();
		for (ServerPref<String> SP : prefs) {
			File f = new File(Servers.getAbsolutePath() + "//" + SP.GuildID);
			try {
				if (f.exists() || f.createNewFile()) {
					ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(f));
					OOS.writeObject(SP);
					OOS.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void importPrefs() {

		if (Servers.listFiles().length > 0) {
			List<File> files = new ArrayList<File>(Arrays.asList(Servers.listFiles()));
			for (File F : files) {

				try {
					ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(F));
					Engine.Servers.put(Long.parseLong(F.getName().trim()),
							(ServerPref<String>) OIS.readObject());
					OIS.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ServerPref<String> importPrefByID(long ID) {
		List<File> f = new ArrayList<File>(Arrays.asList(this.Servers.listFiles()));
		for (File check : f) {
			if (check.getName().contains(Long.toString(ID))) {
				try {
					ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(check));
					ServerPref<String> temp = (ServerPref<String>) OIS.readObject();
					OIS.close();
					return temp;
				} catch (IOException | ClassNotFoundException e) {
					return null;
				}
			}
		}
		return null;
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public void importLists() {
		List<IGuild> guilds = Engine.botClient.getGuilds();
		for (IGuild IG : guilds) {
			try {
				File f = new File(mainStorageDir.getAbsolutePath() + "//" + IG.getLongID() + ".bin");
				if (f.exists() == true && f.isDirectory() == false) {
					System.out.println(IG.getLongID());
					ObjectInputStream OOS = new ObjectInputStream(new FileInputStream(f));
					requestLists.put(IG.getLongID(), (ArrayList<String>) OOS.readObject());
					OOS.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public void importListByID(long ID) {
		File f = new File(mainStorageDir.getAbsolutePath() + File.separatorChar + ID + ".bin");
		if (f.exists()) {
			System.out.println(ID);
			try {
				ObjectInputStream OOS = new ObjectInputStream(new FileInputStream(f));
				requestLists.put(ID, (ArrayList<String>) OOS.readObject());
				OOS.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	@Deprecated
	public void dumpItems() {
		try {
			ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(itemsFile));
			OOS.writeObject(Engine.ItemStorage);
			OOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}