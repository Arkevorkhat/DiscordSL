package botCore;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -219550928877126207L;

	String itemName;
	
	int quantity;
	String unit;
	
	boolean alreadyGotten;
}
