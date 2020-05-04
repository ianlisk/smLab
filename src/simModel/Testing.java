package simModel;

import simulationModelling.Activity;

class Testing extends Activity {

	protected final static int[] NONE = null;

	static SMLabTesting model;

	private int[] ids; // [cid, tid]
	private int shid;
	private double procTime;

	public static boolean precondition() {
		return udpCellToStartTest() != NONE;
	}

	@Override
	public void startingEvent() {
		ids = udpCellToStartTest();
		int cid = ids[0];
		int tid = ids[1];
		model.rcTester[cid][tid].status = Tester.Status.BUSY;
		shid = model.qInputBuffer[cid].spRemoveQue();
		procTime = uTestTime(cid);
	}

	@Override
	public double duration() {
		return procTime;
	}

	@Override
	public void terminatingEvent() {
		model.qOutputBuffer[ids[0]].spInsertQue(shid);
		model.rcTester[ids[0]][ids[1]].status = Tester.Status.IDLE;

		if (ids[0] == Constants.C2) {
			model.rcTester[Constants.C2][ids[1]].numOps += 1;

		} else {
			model.rcTester[ids[0]][ids[1]].timeToFail -= procTime;

		}
		model.rcSampleHolder[shid].sample.step += 1;
	}

	/*
	 * UDPs
	 */
	protected static int[] udpCellToStartTest() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			if (model.qInputBuffer[cid].list.size() > 0) {
				for (int tid = 0; tid < model.numTesters[cid]; tid++) {
					Tester tester = model.rcTester[cid][tid];
					if (tester.status == Tester.Status.IDLE) {
						return new int[] { cid, tid };
					}
				}
			}
		}
		return NONE;
	}

	/*
	 * DVPs
	 */
	protected static double uTestTime(int cid) {
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