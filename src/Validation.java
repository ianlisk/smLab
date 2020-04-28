
import cern.jet.random.engine.RandomSeedGenerator;
import simModel.Constants;
import simModel.SMLabTesting;
import simModel.Seeds;

class Validation {
	public static void main(String[] args) {
		double startTime = 0.0;
		Seeds sds;
		SMLabTesting model; // Simulation object

		// Get uncorrelated seeds
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		sds = new Seeds(rsg);

		System.out.println("Validation\n");
		// Validation for 1 hours
		model = new SMLabTesting(startTime, startTime + 60, new int[] { 5, 5, 4, 5, 4 },
				Constants.INIT_NUM_SAMPLE_HOLDERS, true, sds, true);

		model.runSimulation();

		printOutput(model);
	}

	private static void printOutput(SMLabTesting model) {
		System.out.println("SampleHolders: " + model.numSampleHolders);
		System.out.printf("numTesters: %d %d %d %d %d\n", model.numTesters[0], model.numTesters[1], model.numTesters[2],
				model.numTesters[3], model.numTesters[4]);
		System.out.println("logicConfiguration: " + model.logicConfiguration);
		System.out.printf("  getTotalSample: %d\n", model.getTotalSample());
		System.out.printf("  getOvertimedSample: %d\n", model.getOvertimedSample());
		System.out.printf("  getTurnaroundUnsatisfiedLevel: %.2f\n\n", model.getTurnaroundUnsatisfiedLevel());
	}
}
