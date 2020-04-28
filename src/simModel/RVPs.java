package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import dataModelling.TriangularVariate;

class RVPs {
	static SMLabTesting model; // for accessing the clock
	// Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define
	// reference variables here and create the objects in the
	// constructor with seeds

	// sample arrival means; unit is minute
	protected static final double[] ARRIVAL_MEAN_ARR = new double[] { 0.504201681, 0.560747664, 0.6, 0.530973451,
			0.487804878, 0.517241379, 0.560747664, 0.495867769, 0.458015267, 0.394736842, 0.350877193, 0.314136126, 0.3,
			0.337078652, 0.350877193, 0.394736842, 0.447761194, 0.408163265, 0.363636364, 0.387096774, 0.402684564,
			0.447761194, 0.504201681, 0.517241379 };

	protected double[] FAILURE_MEAN_ARR = new double[] { 14, -1, 9, 15, 16 };
	protected double[] REPAIR_TIME_MEAN_ARR = new double[] { 11, -1, 7, 14, 13 };

	private Exponential sampleArrDist;
	private Exponential timeToFailureDist;
	private Uniform rushSampleDist;
	private Exponential repairTimeDist;

	// Constructor
	protected RVPs(Seeds sd) {
		sampleArrDist = new Exponential(ARRIVAL_MEAN_ARR[0], new MersenneTwister(sd.arr));
		rushSampleDist = new Uniform(0.0, 1.0, new MersenneTwister(sd.rushSampleSeed));
		sequenceTypeDist = new Uniform(0.0, 100.0, new MersenneTwister(sd.sequenceSeed));
		timeToFailureDist = new Exponential((1.0 / (FAILURE_MEAN_ARR[0] * 60)),
				new MersenneTwister(sd.timeToFailureSeeds));
		cleanTimeDist = new TriangularVariate(5.0, 6.0, 10.0, new MersenneTwister(sd.cleanTimeSeed));
		repairTimeDist = new Exponential(1.0 / REPAIR_TIME_MEAN_ARR[0], new MersenneTwister(sd.repairTime));
		loadUnloadTimeDist = new TriangularVariate(0.18, 0.23, 0.45, new MersenneTwister(sd.loadUnloadTime));
	}

	protected double duSampleArr() {
		double mean = ARRIVAL_MEAN_ARR[0];
		for (int h = 1; h <= 24; h++) {
			if (model.getClock() >= (h - 1) * 60 && model.getClock() < h * 60)
				mean = ARRIVAL_MEAN_ARR[h - 1];
		}
		return model.getClock() + sampleArrDist.nextDouble(mean);
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
		return timeToFailureDist.nextDouble(1 / (mean * 60));
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
		return repairTimeDist.nextDouble(1 / mean);
	}

	protected double uRepairCleanTime(int cid) {
		if (cid == Constants.C2) {
			return duCleanTime();
		} else {
			return uTimeToFailure(cid) + uRepairTime(cid);
		}
	}

	protected boolean duRush() {
		return rushSampleDist.nextDouble() < 0.07;
	}

	private Uniform sequenceTypeDist;

	protected int[] uGetSequence() {
		double value = sequenceTypeDist.nextDouble();
		int[] sequence;

		if (value < 9) {
			sequence = new int[] { Constants.C1, Constants.C2, Constants.C4, Constants.C5, Constants.LU };
		} else if (value < 22) {
			sequence = new int[] { Constants.C3, Constants.C4, Constants.C5, Constants.LU };
		} else if (value < 37) {
			sequence = new int[] { Constants.C1, Constants.C2, Constants.C3, Constants.C4, Constants.LU };
		} else if (value < 49) {
			sequence = new int[] { Constants.C4, Constants.C3, Constants.C2, Constants.LU };
		} else if (value < 56) {
			sequence = new int[] { Constants.C2, Constants.C5, Constants.C1, Constants.LU };
		} else if (value < 67) {
			sequence = new int[] { Constants.C4, Constants.C5, Constants.C2, Constants.C3, Constants.LU };
		} else if (value < 81) {
			sequence = new int[] { Constants.C1, Constants.C5, Constants.C3, Constants.C4, Constants.LU };
		} else if (value < 87) {
			sequence = new int[] { Constants.C5, Constants.C3, Constants.C1, Constants.LU };
		} else {
			sequence = new int[] { Constants.C2, Constants.C4, Constants.C5, Constants.LU };
		}
		return sequence;
	}

	private TriangularVariate cleanTimeDist;

	protected double duCleanTime() {
		return cleanTimeDist.next();
	}

	private TriangularVariate loadUnloadTimeDist;

	protected double duloadUnloadTime() {
		return loadUnloadTimeDist.next();
	}

}
