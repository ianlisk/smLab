package simModel;

import simulationModelling.ScheduledAction;

class SampleArrival extends ScheduledAction {
	static SMLabTesting model; // reference to model object

	public SampleArrival(SMLabTesting model) {
		this.model = model;
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
		return model.rvp.duSampleArr();
	}

}
