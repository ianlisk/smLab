package simModel;

class Sample {
	/**
	 * A vector value represents the sequence of testers that the sample will go
	 * through: <x,x,x,LU> where x is one of the cell identifiers (C1 to C5) and
	 * LU is used to flag that the end of the sequence has been reached.
	 */
	protected int[] uSequence;
	protected boolean uRush;
	/**
	 * Indicate the next tester index in the sequence that the sample will be
	 * transported to.
	 */
	protected int step;
	protected double arrivalTime;

	// public String toString() {
	// return "sequence = " + Arrays.toString(uSequence) + "; rush = " + uRush +
	// "; step = " + step + "; time = " + arrivalTime;
	// }
}
