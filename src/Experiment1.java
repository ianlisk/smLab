
// File: Experiment.java
// Description:

import cern.jet.random.engine.RandomSeedGenerator;
import simModel.Constants;
import simModel.LoadUnloadDevice;
import simModel.SMLabTesting;
import simModel.Seeds;

// Main Method: Experiments
// 
class Experiment1 {
	public static void main(String[] args) {
		long startRealTime = System.currentTimeMillis();
		double startTime = 0.0;
		Seeds sds;
		SMLabTesting model; // Simulation object

		// Get uncorrelated seeds
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		sds = new Seeds(rsg);

		System.out.println("Experiment\n");
		// run for 60 days, calculate for the end 30 days after the warm-up
		// period
		model = new SMLabTesting(startTime, startTime + 1440 * 60, new int[] { 2, 2, 2, 2, 3 },
				Constants.INIT_NUM_SAMPLE_HOLDERS, LoadUnloadDevice.logicType.NEW_LOGIC, sds, false);

		model.runSimulation();

		printOutput(model);
		long endTime = System.currentTimeMillis();
		System.out.println("Total running time：" + (endTime - startRealTime) + "ms");
	}

	private static void printOutput(SMLabTesting model) {
		System.out.println("SampleHolders: " + model.numSampleHolders);
		System.out.printf("numTesters: %d %d %d %d %d\n", model.numTesters[0], model.numTesters[1], model.numTesters[2],
				model.numTesters[3], model.numTesters[4]);
		System.out.println("logicConfiguration: " + model.rLoadUnloadDeviceLogicConfiguration);
		System.out.printf("  totalSample: %d\n", model.getTotalSample());
		System.out.printf("  overtimedSample: %d\n", model.getOvertimedSample());
		System.out.printf("  passedSample: %d\n", model.getNumPassedSample());
		System.out.printf("  turnaroundUnatisfiedLevel: %.2f%%\n", model.getTurnaroundUnsatisfiedLevel() * 100);
		double[] occupyingRateOfBufferArr = model.getOccupyingRateOfBuffer();
		System.out.printf("  occupyingRateOfBuffer: %.2f%% %.2f%% %.2f%% %.2f%% %.2f%% %.2f%%\n",
				occupyingRateOfBufferArr[0] * 100, occupyingRateOfBufferArr[1] * 100, occupyingRateOfBufferArr[2] * 100,
				occupyingRateOfBufferArr[3] * 100, occupyingRateOfBufferArr[4] * 100,
				occupyingRateOfBufferArr[5] * 100);
		double max = 0.0;
		int maxIndex = 0;
		for (int i = 0; i < occupyingRateOfBufferArr.length - 1; i++) {
			double rate = occupyingRateOfBufferArr[i];
			if (rate > max) {
				max = rate;
				maxIndex = i;
			}
		}
		System.out.println("The largest input buffer utilization is Cell" + (maxIndex + 1));
	}
}
