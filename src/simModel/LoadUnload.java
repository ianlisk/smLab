package simModel;

import simulationModelling.Activity;

class LoadUnload extends Activity {

	static SMLabTesting model;
	private int sid;
	private static final Sample NONE = null;

	public static boolean precondition() {
		if (model.qInputBuffer[Constants.LU].list.size() > 0 && model.rLoadUnloadDevice.busy == false) {
			return true;
		}
		return false;
	}

	@Override
	public void startingEvent() {
		model.rLoadUnloadDevice.busy = true;
		sid = model.qInputBuffer[Constants.LU].remove();

		if (model.rSampleHolder[sid].sample != NONE) {
			model.output.sampleTested(model.rSampleHolder[sid].sample);
			model.rSampleHolder[sid].sample = NONE;
		}
	}

	@Override
	public double duration() {
		return model.rvp.duloadUnloadTime();
	}

	@Override
	public void terminatingEvent() {

		if (model.qNewSampleBuffer.list.size() > 0) {
			Sample icSample = model.qNewSampleBuffer.removeHead();
			model.rSampleHolder[sid].sample = icSample;
		}
		model.qOutputBuffer[Constants.LU].insert(sid);
		model.rLoadUnloadDevice.busy = false;
	}
}
