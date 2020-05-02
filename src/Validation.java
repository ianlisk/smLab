
import cern.jet.random.engine.RandomSeedGenerator;
import simModel.Constants;
import simModel.LoadUnloadDevice;
import simModel.SMLabTesting;
import simModel.Seeds;

class Validation {
	public static void main(String[] args) {
		long startRealTime = System.currentTimeMillis();
		double startTime = 0.0;
		Seeds sds;
		SMLabTesting model; // Simulation object

		// Get uncorrelated seeds
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		sds = new Seeds(rsg);

		System.out.println("Validation\n");
		// Validation for 1 hours
		model = new SMLabTesting(startTime, startTime + 60, new int[] { 1, 1, 1, 1, 1 },
				Constants.INIT_NUM_SAMPLE_HOLDERS, LoadUnloadDevice.logicType.CURRENT_LOGIC, sds, true);

		model.runSimulation();

		printOutput(model);
		long endTime = System.currentTimeMillis();
		System.out.println("代码运行时间：" + (endTime - startRealTime) + "ms");
	}

	private static void printOutput(SMLabTesting model) {
		System.out.println("SampleHolders: " + model.numSampleHolders);
		System.out.printf("numTesters: %d %d %d %d %d\n", model.numTesters[0], model.numTesters[1], model.numTesters[2],
				model.numTesters[3], model.numTesters[4]);
		System.out.println("logicConfiguration: " + model.logicConfiguration);
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
