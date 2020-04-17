package simModel;

class Tester {
	protected enum Status {
		BUSY, DOWN, IDLE
	};

	protected Status status;
	protected double timeToFail;
	protected int numOps;
}
