package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import dataModelling.TriangularVariate;
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
			model.rcTester[cid][tid].numOps = 0;
	}

	@Override
	public double duration() {
		int cid = ids[0];
		return uRepairCleanTime(cid);
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
					if (testers[tid].numOps == Constants.TESTS_BEFORE_CLEAN) {
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

	/* RVPs */
	protected static double[] REPAIR_TIME_MEAN_ARR = new double[] { 11, -1, 7, 14, 13 };
	private static Exponential repairTimeDist;
	private static TriangularVariate cleanTimeDist;

	protected static void initRvps(Seeds sd) {
		cleanTimeDist = new TriangularVariate(5.0, 6.0, 10.0, new MersenneTwister(sd.cleanTimeSeed));
		repairTimeDist = new Exponential(1.0 / REPAIR_TIME_MEAN_ARR[0], new MersenneTwister(sd.repairTime));
	}

	protected double uRepairTime(int cid) {
		double mean = REPAIR_TIME_MEAN_ARR[0];
		switch (cid) {
		case Constants.C1:
			mean = REPAIR_TIME_MEAN_ARR[0];
		case Constants.C2:
			mean = REPAIR_TIME_MEAN_ARR[1];
		case Constants.C3:
			mean = REPAIR_TIME_MEAN_ARR[2];
		case Constants.C4:
			mean = REPAIR_TIME_MEAN_ARR[3];
		case Constants.C5:
			mean = REPAIR_TIME_MEAN_ARR[4];
		}
		return repairTimeDist.nextDouble(1.0 / mean);
	}

	protected double duCleanTime() {
		return cleanTimeDist.next();
	}

	protected double uRepairCleanTime(int cid) {
		if (cid == Constants.C2) {
			return duCleanTime();
		} else {
			return uRepairTime(cid);
		}
	}

}