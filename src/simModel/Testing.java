package simModel;

import simulationModelling.Activity;

class Testing extends Activity {

	protected final static int[] NONE = {};

	static SMLabTesting model;

	private int[] ids; // [cid, tid]
	private int shid;
	private double procTime;

	public static boolean precondition() {
		return cellToStartTest() != NONE;
	}

	@Override
	public void startingEvent() {
		ids = cellToStartTest();
		model.rcTester[ids[0]][ids[1]].status = Tester.Status.BUSY;
		shid = model.qInputBuffer[ids[0]].remove();
		procTime = model.dvp.uTestTime(ids[0]);
	}

	@Override
	public double duration() {
		return procTime;
	}

	@Override
	public void terminatingEvent() {
		model.qOutputBuffer[ids[0]].insert(shid);
		model.rcTester[ids[0]][ids[1]].status = Tester.Status.IDLE;

		if (ids[0] == Constants.C2) {
			model.rcTester[Constants.C2][ids[1]].numOps += 1;

		} else {
			model.rcTester[ids[0]][ids[1]].timeToFail -= procTime;

		}
		model.rSampleHolder[shid].sample.step += 1;
	}

	/*
	 * UDPs
	 */
	protected static int[] cellToStartTest() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			// System.out.println("********************************Testing
			// cellToStartTest qInputBuffer[" + cid
			// + "].list = " + model.qInputBuffer[cid].list);
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

}