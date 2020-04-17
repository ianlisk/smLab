package simModel;

class Tester {
	protected enum Status {
		BUSY, MAINTENANCE, CLEANING, IDLE
	};

	protected Status status;
	protected double timeToFail;
	protected int numOps;
}
