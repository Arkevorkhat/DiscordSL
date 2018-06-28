package constantStores;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class FileConst {
	public static File baseFile = FileSystemView.getFileSystemView().getHomeDirectory();
	public static File winFile = new File(baseFile.getParentFile().getParent() + File.separatorChar + "Appdata" + File.separatorChar
			+ "Roaming" + File.separatorChar + "." + StringConst.botName);
	public static File macFile = new File(baseFile.getAbsolutePath()+File.separatorChar+"."+StringConst.botName);
	public static File linFile = new File("~/home/DiscordShoppingList");
}