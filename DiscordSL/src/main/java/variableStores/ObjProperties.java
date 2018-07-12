package variableStores;

import java.io.File;
import java.io.Serializable;

import javax.swing.filechooser.FileSystemView;

public class ObjProperties implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3349541138957889959L;
	private String commandPrefix;
	private String entryDelimiter;
	private File coreDirectory = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
			+ File.separatorChar + constantStores.StringConst.botName);

	
	public String setPrefByName(String preferenceName, String setTo) {
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
	public String getCommandPrefix() {
		return commandPrefix;
	}
	public void setCommandPrefix(String commandPrefix) {
		this.commandPrefix = commandPrefix;
	}
	public String getEntryDelimiter() {
		return entryDelimiter;
	}
	public void setEntryDelimiter(String entryDelimiter) {
		this.entryDelimiter = entryDelimiter;
	}
	public File getCoreDirectory() {
		return coreDirectory;
	}
	public void setCoreDirectory(File coreDirectory) {
		this.coreDirectory = coreDirectory;
	}
}
