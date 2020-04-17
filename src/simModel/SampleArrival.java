package simModel;

import simulationModelling.ScheduledAction;

class SampleArrival extends ScheduledAction {
	SMLabTesting model; // reference to model object

	public SampleArrival(SMLabTesting model) {
		this.model = model;
	}

	public void actionEvent() {
		// WArrival Action Sequence SCS
		Sample icSample = new Sample();
		// icSample.uType = model.rvp.uCustomerType();
		icSample.time = model.getClock();
		model.qNewSample.add(icSample);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationModelling.ScheduledAction#timeSequence()
	 */
	@Override
	protected double timeSequence() {
		return model.rvp.duSampleArrival();
	}
	

}
