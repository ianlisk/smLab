package simModel;


import simulationModelling.SequelActivity;

class Move extends SequelActivity {
	static SMLabTesting model;

	private static final double DURATION = 1.0;
	private static final int LOOP_SIZE = 48;

	@Override
	protected double duration() {
		return DURATION;
	}

	@Override
	public void startingEvent() {
		// NONE
	}

	@Override
	protected void terminatingEvent() {

		//TODO
		moveToFromLoop();

		model.spStart(new Move());
	}

	/* UDPs */
	/**
	 * Compute the Enter and Exit position of each input/output buffer of test
	 * cell c1 to c5 and the Load/Unload area with this table (let the
	 * TransportationLoop.loadAreaPosition be pid)
	 */
	protected void moveToFromLoop() {
		//TODO
	}
}