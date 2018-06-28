package botCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import constantStores.FileConst;

public class FileHandler {
	public File mainStorageDir = variableStores.Properties.getCoreDirectory();
	public File itemsFile;
	public final File prefsFile;

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
		System.out.println(mainStorageDir.mkdirs()+mainStorageDir.getAbsolutePath());
		itemsFile = new File(mainStorageDir.getAbsolutePath() + "//Items.bin");
		prefsFile = new File(mainStorageDir.getAbsolutePath() + "//Bot.properties");
		
		try {
			System.out.println(itemsFile.createNewFile());
			System.out.println(prefsFile.createNewFile());
		} catch (IOException e) {
			if (e.getMessage().contains("Exists")) {
				System.out.println("File already exists, Skipping.");
			} else
				System.out.println(e.getMessage());
		}
	}

	public void setDirs() {
		
	}

	public void loadPrefs() {
		Preferences UsrPrefs = Preferences.userRoot();
		variableStores.Properties.setCommandPrefix(UsrPrefs.get("commandPrefix","&"));
		variableStores.Properties.setEntryDelimiter(UsrPrefs.get("entryDelimiter", ";"));
		variableStores.Properties.setDelimEntrySplit(UsrPrefs.get("delimitedEntrySplitter",","));
	}

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