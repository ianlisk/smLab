package simModel;

import simulationModelling.Activity;

class RepairClean extends Activity {
	static SMLabTesting model;
	protected final static int[] NONE = {};
	private int[] ids; //[cid, tid]

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
		return model.rvp.uGetFixTime(ids[0]) + model.rcTesters[ids[0]][ids[1]].timeToFail;
	}

	@Override
	public void terminatingEvent() {
		model.rcTesters[ids[0]][ids[1]].Tester.Status = Tester.Status.IDLE;
		model.rcTesters[ids[0]][ids[1]].timeToFail = model.rvp.uGetTimeToFailure(ids[0]);
	}

	/* UDPs */
	protected static int[] testerRequireRC() {

		for (int cid : Constants.DEFAULT_CID_ARRAY) {

			if (cid == Constants.C2)
				continue;

			for (int tid = 0; tid < model.numberOfTesters[cid]; tid++) {
				if (model.rcTesters[cid][tid].Tester.Status != Tester.Status.DOWN
						&& model.rcTesters[cid][tid].timeToFail < model.dvp.uGetTestTime(cid)) {
					return new Pair<Integer>(cid, tid);
				}
			}
		}

		return NONE;
	}

	// GAComment: Can simply use an integer array.
	private static class Pair<T> {
		T cid, tid;

		public Pair(T cid, T tid) {
			this.cid = cid;
			this.tid = tid;
		}
	}
}