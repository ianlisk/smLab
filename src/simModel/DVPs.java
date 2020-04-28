package simModel;

class DVPs {
	static SMLabTesting model; // for accessing the clock

	// Constructor
	protected DVPs(SMLabTesting model) {
		this.model = model;
	}

	// Translate deterministic value procedures into methods
	protected double uTestTime(int cid) {
		switch (cid) {
		case Constants.C1:
			return 0.77;
		case Constants.C2:
			return 0.85;
		case Constants.C3:
			return 1.03;
		case Constants.C4:
			return 1.24;
		case Constants.C5:
			return 1.7;
		}
		return 0.0;
	}
}
