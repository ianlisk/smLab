package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ScheduledAction;

class SampleArrival extends ScheduledAction {
	static SMLabTesting model; // reference to model object

	static private Exponential sampleArrDist;
	private static Uniform rushSampleDist;
	private static Uniform sequenceTypeDist;

	// sample arrival means; unit is minute
	protected static final double[] ARRIVAL_MEAN_ARR = new double[] { 0.504201681, 0.560747664, 0.6, 0.530973451,
			0.487804878, 0.517241379, 0.560747664, 0.495867769, 0.458015267, 0.394736842, 0.350877193, 0.314136126, 0.3,
			0.337078652, 0.350877193, 0.394736842, 0.447761194, 0.408163265, 0.363636364, 0.387096774, 0.402684564,
			0.447761194, 0.504201681, 0.517241379 };

	static void initRvps(Seeds sd) {
		// Initialise Internal modules, user modules and input variables
		sampleArrDist = new Exponential(1.0 / ARRIVAL_MEAN_ARR[0], new MersenneTwister(sd.arr));
		rushSampleDist = new Uniform(0.0, 1.0, new MersenneTwister(sd.rushSampleSeed));
		sequenceTypeDist = new Uniform(0.0, 1.0, new MersenneTwister(sd.sequenceSeed));
	}

	@Override
	public void actionEvent() {
		Sample icSample = new Sample();
		icSample.arrivalTime = model.getClock();
		icSample.uRush = duRush();
		icSample.step = 0;
		icSample.uSequence = uGetSequence();
		if (icSample.uRush == true) {
			model.qNewSampleBuffer.insertRushSample(icSample);

		} else {
			model.qNewSampleBuffer.spInsertQue(icSample);
		}
	}

	@Override
	protected double timeSequence() {
		return duSampleArr();
	}

	protected double duSampleArr() {
		double mean = ARRIVAL_MEAN_ARR[0];
		double t = model.getClock();
		for (int h = 1; h <= 24; h++) {
			if (t >= (h - 1) * 60 && t < h * 60) {
				mean = ARRIVAL_MEAN_ARR[h - 1];
				break;
			}
		}
		return t + sampleArrDist.nextDouble(1.0 / mean);
	}

	/* UDP */
	protected void addSampleToNewSampleQue(Sample icSample) {
		if (icSample.uRush == true) {
			model.qNewSampleBuffer.insertRushSample(icSample);

		} else {
			model.qNewSampleBuffer.spInsertQue(icSample);

		}
	}

	protected boolean duRush() {
		return rushSampleDist.nextDouble() < 0.07;
	}

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

}
