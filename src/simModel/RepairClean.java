package simModel;

import simulationModelling.Activity;

class RepairClean extends Activity {
	static SMLabTesting model;
	protected final static int[] NONE = {};
	private int[] ids; // [cid, tid]

	public static boolean precondition() {
		return testerRequireRC() != NONE;
	}

	@Override
	public void startingEvent() {
		ids = testerRequireRC();
		model.rcTester[ids[0]][ids[1]].status = Tester.Status.DOWN;
	}

	@Override
	public double duration() {
		return model.rvp.uRepairTime(ids[0]) + model.rcTester[ids[0]][ids[1]].timeToFail;
	}

	@Override
	public void terminatingEvent() {
		model.rcTester[ids[0]][ids[1]].status = Tester.Status.IDLE;
		model.rcTester[ids[0]][ids[1]].timeToFail = model.rvp.uTimeToFailure(ids[0]);
	}

	/* UDPs */
	protected static int[] testerRequireRC() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			if (cid == Constants.C2)
				continue;
			for (int tid = 0; tid < model.numTesters[cid]; tid++) {
				if (model.rcTester[cid][tid].status != Tester.Status.DOWN
						&& model.rcTester[cid][tid].timeToFail < model.dvp.uTestTime(cid)) {
					return new int[] { cid, tid };
				}
			}
		}
		return NONE;
	}

}