package simModel;

public class LoadUnloadDevice {

	/**
	 * Indicates the status of the Load/Unload device. TRUE when busy and FALSE
	 * otherwise.
	 */
	protected boolean busy;

	enum logicType {
		CURRENT_LOGIC, NEW_LOGIC
	};

	/**
	 * True if NEW_LOGIC will be added, False otherwise
	 */
	protected boolean logicConfiguration;

}
