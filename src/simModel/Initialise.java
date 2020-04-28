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

	// 最终效果是传送带上随机分布着sh，sh序号作为数组元素存储在model.rqTransportationLoop.position中
	private void udpPlaceSampleHolder() {
		int num = model.numSampleHolders - 1; // SampleHolder个数，default 20，
												// num代表sh序号
		System.out.println("*************************udpPlaceSampleHolder num = " + num);
		if (model.logicConfiguration == false) {
			while (model.qInputBuffer[Constants.LU].isAvailable() && num >= 0) {
				model.qInputBuffer[Constants.LU].insert(num);
				num -= 1;
			}
		} else {
			while (model.qInputBuffer[Constants.LU].list.size() < Constants.LU_BUFFER_SUGGESTED_WAITING_NUMBER
					&& num >= 0) {
				model.qInputBuffer[Constants.LU].insert(num);
				num -= 1;
			}
		}
		// 生成0-47个整数序列
		ArrayList<Integer> positions = new ArrayList<>();
		for (int i = 0; i < Constants.LOOP_SIZE; i++) {
			positions.add(i);
		}
		System.out.println("*************************udpPlaceSampleHolder positions = " + positions.toString());
		Collections.shuffle(positions);// 将上述序列重新随机排列
		System.out.println("*************************udpPlaceSampleHolder positions = " + positions.toString());
		System.out.println("*************************udpPlaceSampleHolder rqTransportLoop.list = "
				+ Arrays.toString(model.rqTransportationLoop.position));
		for (int pos : positions) {
			System.out.println("num = " + num);
			model.rqTransportationLoop.position[pos] = num;
			num -= 1;
			if (num < 0)
				break; // 将num值随机赋给传送带中num个单元
		}

		System.out.println("*************************udpPlaceSampleHolder rqTransportLoop.list = "
				+ Arrays.toString(model.rqTransportationLoop.position));
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
