package simModel;

import simulationModelling.Activity;

class RepairClean extends Activity {
	static SMLabTesting model;
	protected final static int[] NONE = {};
	private int[] ids; // [cid, tid]

	public static boolean precondition() {
		return udpTesterRequiredRC() != NONE;
	}

	@Override
	public void startingEvent() {
		ids = udpTesterRequiredRC();
		int cid = ids[0];
		int tid = ids[1];
		model.rcTester[cid][tid].status = Tester.Status.DOWN;
		if (cid == Constants.C2)
			model.rcTester[cid][tid].numTests = 0;
	}

	@Override
	public double duration() {
		int cid = ids[0];
		int tid = ids[1];
		// return model.rvp.uRepairTime(cid) +
		// model.rcTester[cid][tid].timeToFail;
		return model.rvp.uRepairCleanTime(cid);
	}

	@Override
	public void terminatingEvent() {
		int cid = ids[0];
		int tid = ids[1];
		model.rcTester[cid][tid].status = Tester.Status.IDLE;
		if (cid != Constants.C2)
			model.rcTester[cid][tid].timeToFail = model.rvp.uTimeToFailure(cid);
	}

	/* UDPs */
	protected static int[] udpTesterRequiredRC() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			Tester[] testers = model.rcTester[cid];
			if (cid == Constants.C2) {
				for (int tid = 0; tid < testers.length; tid++) {
					if (testers[tid].numTests == Constants.TESTS_BEFORE_CLEAN) {
						return new int[] { cid, tid };
					}
				}
			} else {
				for (int tid = 0; tid < model.numTesters[cid]; tid++) {
					if (testers[tid].status != Tester.Status.DOWN
							&& testers[tid].timeToFail < model.testing.uTestTime(cid)) {
						return new int[] { cid, tid };
					}
				}
			}
		}
		return NONE;
	}

}