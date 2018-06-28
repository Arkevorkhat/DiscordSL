package variableStores;

import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.filechooser.FileSystemView;

public class Properties {
	public static String commandPrefix = "&";
	public static String entryDelimiter = " ";
	public static File coreDirectory = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
			+ File.separatorChar + constantStores.StringConst.botName);

	public static void loadPrefs() {
		setCommandPrefix(Preferences.userRoot().get("commandPrefix", "&"));
		setEntryDelimiter(Preferences.userRoot().get("entryDelimiter", " "));
	}

	public static void setCommandPrefix(String replace) {
		commandPrefix = replace;
		Preferences.userRoot().put("commandPrefix", replace);
	}

	public static String getCommandPrefix() {
		return commandPrefix;
	}

	public static String getEntryDelimiter() {
		return entryDelimiter;
	}

	public static void setEntryDelimiter(String entryDelimiter) {
		Properties.entryDelimiter = entryDelimiter;
		Preferences.userRoot().put("entryDelimiter", entryDelimiter);
	}

	public static File getCoreDirectory() {
		return coreDirectory;
	}

	public static void setCoreDirectory(File coreDirectory) {
		Properties.coreDirectory = coreDirectory;
	}

	public static String setPrefByName(String preferenceName, String setTo) {
		switch (preferenceName.toLowerCase()) {
		case "commandprefix":
			setCommandPrefix(setTo);
			return "Preference \"commandPrefix\" successfully set to: " + setTo;
		case "entryDelimiter":
			setEntryDelimiter(setTo);
			return "Preference \"entryDelimiter\" successfully set to: " + setTo;
		default:
			return "failed to find preference \"" + preferenceName + "\" No changes made.";
		}
	}
}
