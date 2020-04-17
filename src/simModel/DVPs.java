package simModel;

class DVPs {
	SMLabTesting model; // for accessing the clock

	// Constructor
	protected DVPs(SMLabTesting model) {
		this.model = model;
	}

	// Translate deterministic value procedures into methods
	protected double uTestTime(int cid) {
		switch(cid){
			case Constants.C1:
				return 0.77 * 60;
			case Constants.C2:
				return 0.85 * 60;
			case Constants.C3:
				return 1.03 * 60;
			case Constants.C4:
				return 1.24 * 60;
			case Constants.C5:
				return 1.7 * 60;
		}
		return 0.0;
	}
}
