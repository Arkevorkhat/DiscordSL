package variableStores;

import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.filechooser.FileSystemView;

public class Properties {
	public static String commandPrefix = "&";
	public static String entryDelimiter = " ";
	public static String delimEntrySplit = ",";
	public static File coreDirectory = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
			+ File.separatorChar + constantStores.StringConst.botName);
	
	public static void loadPrefs() {
		setCommandPrefix(Preferences.userRoot().get("commandPrefix", "&"));
		setEntryDelimiter(Preferences.userRoot().get("entryDelimiter", ";"));
		setDelimEntrySplit(Preferences.userRoot().get("delimEntrySplit", ","));
	}
	public static void setCommandPrefix(String replace) {
		commandPrefix = replace;
	}
	public static String getCommandPrefix() {
		return commandPrefix;
	}
	public static String getEntryDelimiter() {
		return entryDelimiter;
	}
	public static void setEntryDelimiter(String entryDelimiter) {
		Properties.entryDelimiter = entryDelimiter;
	}
	public static String getDelimEntrySplit() {
		return delimEntrySplit;
	}
	public static void setDelimEntrySplit(String delimEntrySplit) {
		Properties.delimEntrySplit = delimEntrySplit;
	}
	public static File getCoreDirectory() {
		return coreDirectory;
	}
	public static void setCoreDirectory(File coreDirectory) {
		Properties.coreDirectory = coreDirectory;
	}
}
