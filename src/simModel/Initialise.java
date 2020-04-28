package simModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import simModel.Tester.Status;
import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction {
	static SMLabTesting model;
	private static final int NONE = -1;
	private static final Sample NO_SAMPLE = null;

	protected Initialise(SMLabTesting model) {
		this.model = model;
	}

	double[] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0; // set index to first entry.

	public double timeSequence() {
		return ts[tsix++]; // only invoked at t=0
	}

	protected void actionEvent() {
		udpInitInputBuffer();
		udpInitOutputBuffer();
		udpInitSampleHolders();
		udpInitLoadUnloadDevice();
		udpInitTester();
		udpInitTransportationLoop();
		udpInitNewSampleBuffer();
		udpPlaceSampleHolder();
	}

	private void udpInitSampleHolders() {
		model.rSampleHolder = new SampleHolder[model.numSampleHolders];
		for (int sid = 0; sid < model.numSampleHolders; sid++) {
			SampleHolder sampleHolders = new SampleHolder();
			sampleHolders.sample = NO_SAMPLE;
			model.rSampleHolder[sid] = sampleHolders;
		}
	}

	private void udpInitLoadUnloadDevice() {
		LoadUnloadDevice ld = new LoadUnloadDevice();
		ld.busy = false;
		model.rLoadUnloadDevice = ld;
	}

	private void udpInitTester() {
		model.rcTester = new Tester[5][];
		for (int cid : Constants.DEFAULT_CID_ARRAY) {

			model.rcTester[cid] = new Tester[model.numTesters[cid]];
			for (int tid = 0; tid < model.numTesters[cid]; tid++) {
				Tester t = new Tester();
				t.status = Status.IDLE;
				t.timeToFail = model.rvp.uTimeToFailure(cid);
				t.numOps = 0;
				model.rcTester[cid][tid] = t;
			}
		}
	}

	private void udpInitTransportationLoop() {
		for (int pos = 0; pos < Constants.LOOP_SIZE; pos++) {
			model.rqTransportationLoop.position[pos] = NONE;
		}
		model.rqTransportationLoop.loadAreaPosition = 0;
	}

	private void udpInitNewSampleBuffer() {
		model.qNewSampleBuffer.list = new ArrayList<>();
	}

	private void udpPlaceSampleHolder() {
		int shNum = model.numSampleHolders - 1; // SampleHolder个数，default 20，
		// loadUnload area
		if (model.logicConfiguration == false) {
			while (model.qInputBuffer[Constants.LU].isAvailable() && shNum >= 0) {
				model.qInputBuffer[Constants.LU].insert(shNum);
				shNum--;
			}
		} else {
			while (model.qInputBuffer[Constants.LU].list.size() < Constants.LU_BUFFER_SUGGESTED_WAITING_NUMBER
					&& shNum >= 0) {
				model.qInputBuffer[Constants.LU].insert(shNum);
				shNum--;
			}
		}
		// transportationLoop
		sequencePlaceSh2Loop(shNum);
		System.out.println("*************************udpPlaceSampleHolder rqTransportLoop.list = "
				+ Arrays.toString(model.rqTransportationLoop.position));
	}

	private void sequencePlaceSh2Loop(int shNum) {
		for (int i = 0; i < Constants.LOOP_SIZE; i++) {
			model.rqTransportationLoop.position[i] = shNum;
			shNum--;
			if (shNum < 0)
				break;
		}
	}

	/**
	 * sh is randomly distributed on the conveyor belt, and the sh sequence
	 * number is stored as an array element in
	 * model.rqTransportationLoop.position
	 */
	private void randomPlaceSh2Loop(int shNum) {
		// Generate integer sequence, range 0-47
		ArrayList<Integer> positions = new ArrayList<>();
		for (int i = 0; i < Constants.LOOP_SIZE; i++) {
			positions.add(i);
		}
		Collections.shuffle(positions);// Rearrange the above sequence randomly
		// System.out.println("*************************udpPlaceSampleHolder
		// rqTransportLoop.list = "
		// + Arrays.toString(model.rqTransportationLoop.position));
		for (int pos : positions) {
			System.out.println("num = " + shNum);
			model.rqTransportationLoop.position[pos] = shNum;
			shNum--;
			if (shNum < 0)
				break; // Randomly assign shNum units in the conveyor belt
		}
	}

	private void udpInitInputBuffer() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			InputBuffer inBuf = new InputBuffer();
			inBuf.capacity = Constants.INPUT_BUFFER_SIZE;
			inBuf.list = new ArrayList<>();
			model.qInputBuffer[cid] = inBuf;
		}
		InputBuffer inBuf = new InputBuffer();
		inBuf.capacity = Constants.LU_INPUT_BUFFER_LENGTH;
		inBuf.list = new ArrayList<>();
		model.qInputBuffer[Constants.LU] = inBuf;
	}

	private void udpInitOutputBuffer() {
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			OutputBuffer ob = new OutputBuffer();
			ob.list = new ArrayList<>();
			model.qOutputBuffer[cid] = ob;
		}
		OutputBuffer ob = new OutputBuffer();
		ob.list = new ArrayList<>();
		model.qOutputBuffer[Constants.LU] = ob;
	}

}
