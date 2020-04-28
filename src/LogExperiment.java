
import cern.jet.random.engine.RandomSeedGenerator;
import simModel.Constants;
import simModel.SMLabTesting;
import simModel.Seeds;

public class LogExperiment {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i, NUMRUNS = 1;
		double startTime = 0.0;
		double endTime = 1 * 24 * 60; // run for 1 days
		Seeds sds;
		SMLabTesting model; // Simulation object

		// Lets get a set of uncorrelated seeds, different seeds for each run
		// RandomSeedGenerator rsg = new RandomSeedGenerator();
		// for (i = 0; i < NUMRUNS; i++)
		// sds[i] = new Seeds(rsg);
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		sds = new Seeds(rsg);

		// Loop for NUMRUN simulation runs for each case
		int lc2 = 3;
		int lc3 = 3;
		System.out.println("Conveyor Limits: " + lc2 + ", " + lc3);
		printDSOVsHeader();
		for (i = 0; i < NUMRUNS; i++) {
			model = new SMLabTesting(startTime, startTime + 1440, new int[] { 4, 5, 4, 5, 4 },
					Constants.INIT_NUM_SAMPLE_HOLDERS, true, sds, true);
			model.runSimulation();
			printDSOVs(i + 1, model);
		}
	}

	private static void printDSOVsHeader() {
		System.out.println("Run, AverageM1Down, AverageConvM2Full, AveragemConvM3Full");
	}

	private static void printDSOVs(int num, SMLabTesting model) {
		System.out.println(num + ", " + model.getOvertimedSample() + ", " + model.getStopNoticeCount() + ", "
				+ model.getTurnaroundUnsatisfiedLevel());
	}
}
