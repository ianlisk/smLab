package simModel;

class Output {
	SMLabTesting model;

	protected Output(SMLabTesting md) {
		model = md;
	}
	// Use OutputSequence class to define Trajectory and Sample Sequences
	// Trajectory Sequences

	// Sample Sequences
	//撒大苏打撒旦
	// DSOVs available in the OutputSequence objects
	// If seperate methods required to process Trajectory or Sample
	// Sequences - add them here

	// SSOVs
	private int totalSample;
	private int overtimedSample;
	private double turnaroundUnsatisfiedLevel;

	public int getTotalSample() {
		return totalSample;
	}
	public int getOvertimedSample() {
		return overtimedSample;
	}
	public double getTurnaroundUnsatisfiedLevel() {
		return turnaroundUnsatisfiedLevel;
	}
}
