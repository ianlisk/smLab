package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;

class RVPs {
	static SMLabTesting model; // for accessing the clock
	// Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define
	// reference variables here and create the objects in the
	// constructor with seeds

	protected double[] FAILURE_MEAN_ARR = new double[] { 14, -1, 9, 15, 16 };

	private Exponential timeToFailureDist;

	// Constructor
	protected RVPs(Seeds sd) {
		timeToFailureDist = new Exponential((1.0 / (FAILURE_MEAN_ARR[0] * 60)),
				new MersenneTwister(sd.timeToFailureSeeds));
	}

	protected double uTimeToFailure(int cid) {
		double mean = FAILURE_MEAN_ARR[0];
		switch (cid) {
		case Constants.C1:
			mean = FAILURE_MEAN_ARR[0];
		case Constants.C2:
			mean = FAILURE_MEAN_ARR[1];
		case Constants.C3:
			mean = FAILURE_MEAN_ARR[2];
		case Constants.C4:
			mean = FAILURE_MEAN_ARR[3];
		case Constants.C5:
			mean = FAILURE_MEAN_ARR[4];
		}
		return timeToFailureDist.nextDouble(1.0 / (mean * 60));
	}

}
