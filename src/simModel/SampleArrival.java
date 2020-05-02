package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ScheduledAction;

class SampleArrival extends ScheduledAction {
	static SMLabTesting model; // reference to model object

	// public SampleArrival(SMLabTesting model) {
	// this.model = model;
	// }
	static private Exponential sampleArrDist;

	// sample arrival means; unit is minute
	protected static final double[] ARRIVAL_MEAN_ARR = new double[] { 0.504201681, 0.560747664, 0.6, 0.530973451,
			0.487804878, 0.517241379, 0.560747664, 0.495867769, 0.458015267, 0.394736842, 0.350877193, 0.314136126, 0.3,
			0.337078652, 0.350877193, 0.394736842, 0.447761194, 0.408163265, 0.363636364, 0.387096774, 0.402684564,
			0.447761194, 0.504201681, 0.517241379 };

	static void initRvps(Seeds sd) {
		// Initialise Internal modules, user modules and input variables
		sampleArrDist = new Exponential(1.0 / ARRIVAL_MEAN_ARR[0], new MersenneTwister(sd.arr));
	}

	@Override
	public void actionEvent() {
		// Sample icSample = new Sample();
		// icSample.time = model.getClock();
		// model.qNewSample.add(icSample);

		Sample iCSample = new Sample();
		iCSample.time = model.getClock();
		iCSample.rush = model.rvp.duRush();
		iCSample.step = 0;
		iCSample.sequence = model.rvp.uGetSequence();
		if (iCSample.rush == true) {
			model.qNewSampleBuffer.addHead(iCSample);

		} else {
			model.qNewSampleBuffer.addTail(iCSample);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationModelling.ScheduledAction#timeSequence()
	 */
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

}
