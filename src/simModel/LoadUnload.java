package simModel;

import cern.jet.random.engine.MersenneTwister;
import dataModelling.TriangularVariate;
import simulationModelling.Activity;

class LoadUnload extends Activity {

	static SMLabTesting model;
	private int sid;
	private static final Sample NONE = null;

	public static boolean precondition() {
		if (model.qInputBuffer[Constants.LU].getN() > 0 && model.rLoadUnloadDevice.busy == false) {
			return true;
		}
		return false;
	}

	@Override
	public void startingEvent() {
		model.rLoadUnloadDevice.busy = true;
		sid = model.qInputBuffer[Constants.LU].spRemoveQue();

		if (model.rcSampleHolder[sid].sample != NONE) {
			model.output.sampleTested(model.rcSampleHolder[sid].sample);
			model.rcSampleHolder[sid].sample = NONE;
		}
	}

	@Override
	public double duration() {
		return duloadUnloadTime();
	}

	@Override
	public void terminatingEvent() {

		if (model.qNewSampleBuffer.list.size() > 0) {
			Sample icSample = model.qNewSampleBuffer.spRemoveQue();
			model.rcSampleHolder[sid].sample = icSample;
		}
		model.qOutputBuffer[Constants.LU].spInsertQue(sid);
		model.rLoadUnloadDevice.busy = false;
	}

	/* RVPs */
	static void initRvps(Seeds sd) {
		loadUnloadTimeDist = new TriangularVariate(0.18, 0.23, 0.45, new MersenneTwister(sd.loadUnloadTime));
	}

	private static TriangularVariate loadUnloadTimeDist;

	protected double duloadUnloadTime() {
		return loadUnloadTimeDist.next();
	}
}
