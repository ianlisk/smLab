package simModel;

class Output {
	static SMLabTesting model;

	protected Output(SMLabTesting md) {
		model = md;
	}
	// Use OutputSequence class to define Trajectory and Sample Sequences
	// Trajectory Sequences

	// Sample Sequences
	// DSOVs available in the OutputSequence objects
	// If seperate methods required to process Trajectory or Sample
	// Sequences - add them here

	// SSOVs
	private int totalSample;
	private int overtimedSample;
	private double turnaroundUnsatisfiedLevel;
	// DSOV

	protected int numRushSample;
	protected int numRegularSample;
	protected int numRushSamplePass;
	protected int numRegularSamplePass;

	public Output() {
		numRushSample = 0;
		numRegularSample = 0;
		numRushSamplePass = 0;
		numRegularSamplePass = 0;
	}

	public int getTotalSample() {
		totalSample = this.numRegularSample + this.numRushSample;
		return totalSample;
	}

	public int getOvertimedSample() {
		overtimedSample = (this.numRegularSample - this.numRegularSamplePass)
				+ (this.numRushSample - this.numRushSamplePass);
		return overtimedSample;
	}

	public double getTurnaroundUnsatisfiedLevel() {
		turnaroundUnsatisfiedLevel = 100.0 * (this.numRegularSamplePass + this.numRushSamplePass)
				/ (this.numRegularSample + this.numRushSamplePass);
		return turnaroundUnsatisfiedLevel;
	}

	//
	protected void SampleTested(Sample sample) {
		double timeTested = model.getClock() - sample.time;
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$timeTested = " + timeTested);
		if (sample.rush == false) {
			this.numRegularSample = this.numRegularSample + 1;
			if (timeTested < 60.0)
				this.numRegularSamplePass = this.numRegularSamplePass + 1;
		} else {
			this.numRushSample = this.numRushSample + 1;
			if (timeTested < 30.0)
				this.numRushSamplePass = this.numRushSamplePass + 1;
		}
	}
}
