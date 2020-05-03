
import cern.jet.random.engine.RandomSeedGenerator;
import outputAnalysis.ConfidenceInterval;
import simModel.Constants;
import simModel.LoadUnloadDevice;
import simModel.SMLabTesting;
import simModel.Seeds;

public class Experiment2 {
	// Create matrix of schedules
	static int[] testers = new int[] { 10, 10, 10, 10, 10 };
	// Experimentation constants
	public static final double CONF_LEVEL = 0.950;

	final static int[] NUM_RUNS_ARRAY = { 10, 20, 30, 60 };

	public static void main(String[] args) {
		int i;
		double startTime = 0.0, endTime = 1440.0;
		int numRuns1 = NUM_RUNS_ARRAY[0];
		int numRuns2 = NUM_RUNS_ARRAY[1];
		int numRuns3 = NUM_RUNS_ARRAY[2];
		int numRuns4 = NUM_RUNS_ARRAY[3];
		SMLabTesting model; // Simulation object
		double[] valuesCase1 = new double[numRuns1];
		double[] valuesCase2 = new double[numRuns2];
		double[] valuesCase3 = new double[numRuns3];
		double[] valuesCase4 = new double[numRuns4];

		// Lets get a set of uncorrelated seeds
		RandomSeedGenerator rsg = new RandomSeedGenerator();

		// Loop for NUMRUN simulation runs for each case
		// Case 1
		Seeds[] sds1 = new Seeds[numRuns1];
		for (i = 0; i < numRuns1; i++)
			sds1[i] = new Seeds(rsg);
		for (i = 0; i < numRuns1; i++) {
			model = new SMLabTesting(startTime, endTime, testers, Constants.INIT_NUM_SAMPLE_HOLDERS,
					LoadUnloadDevice.logicType.CURRENT_LOGIC, sds1[i], false);
			model.runSimulation();
			valuesCase1[i] = model.getTurnaroundUnsatisfiedLevel();
		}
		// Case 2
		Seeds[] sds2 = new Seeds[numRuns2];
		for (i = 0; i < numRuns2; i++)
			sds2[i] = new Seeds(rsg);
		for (i = 0; i < numRuns2; i++) {
			model = new SMLabTesting(startTime, endTime, testers, Constants.INIT_NUM_SAMPLE_HOLDERS,
					LoadUnloadDevice.logicType.CURRENT_LOGIC, sds2[i], false);
			model.runSimulation();
			valuesCase2[i] = model.getTurnaroundUnsatisfiedLevel();
		}
		// Case 3
		Seeds[] sds3 = new Seeds[numRuns3];
		for (i = 0; i < numRuns3; i++)
			sds3[i] = new Seeds(rsg);
		for (i = 0; i < numRuns3; i++) {
			model = new SMLabTesting(startTime, endTime, testers, Constants.INIT_NUM_SAMPLE_HOLDERS,
					LoadUnloadDevice.logicType.CURRENT_LOGIC, sds3[i], false);
			model.runSimulation();
			valuesCase3[i] = model.getTurnaroundUnsatisfiedLevel();
		}
		// Case 4
		Seeds[] sds4 = new Seeds[numRuns4];
		for (i = 0; i < numRuns4; i++)
			sds4[i] = new Seeds(rsg);
		for (i = 0; i < numRuns4; i++) {
			model = new SMLabTesting(startTime, endTime, testers, Constants.INIT_NUM_SAMPLE_HOLDERS,
					LoadUnloadDevice.logicType.CURRENT_LOGIC, sds4[i], false);
			model.runSimulation();
			valuesCase4[i] = model.getTurnaroundUnsatisfiedLevel();
		}

		// Get the conficence intervals
		ConfidenceInterval cf1 = new ConfidenceInterval(valuesCase1, CONF_LEVEL);
		ConfidenceInterval cf2 = new ConfidenceInterval(valuesCase2, CONF_LEVEL);
		ConfidenceInterval cf3 = new ConfidenceInterval(valuesCase3, CONF_LEVEL);
		ConfidenceInterval cf4 = new ConfidenceInterval(valuesCase4, CONF_LEVEL);
		// Create the table
		System.out.printf("-------------------------------------------------------------------------------------\n");
		System.out.printf("Num runs    Point estimate(ybar(n))  s(n)     zeta   CI Min   CI Max |zeta/ybar(n)|\n");
		System.out.printf("-------------------------------------------------------------------------------------\n");
		System.out.printf("    10 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", cf1.getPointEstimate(), cf1.getVariance(),
				cf1.getZeta(), cf1.getCfMin(), cf1.getCfMax(), Math.abs(cf1.getZeta() / cf1.getPointEstimate()));
		System.out.printf("    20 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", cf2.getPointEstimate(), cf2.getVariance(),
				cf2.getZeta(), cf2.getCfMin(), cf2.getCfMax(), Math.abs(cf2.getZeta() / cf2.getPointEstimate()));
		System.out.printf("    30 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", cf3.getPointEstimate(), cf3.getVariance(),
				cf3.getZeta(), cf3.getCfMin(), cf3.getCfMax(), Math.abs(cf3.getZeta() / cf3.getPointEstimate()));
		System.out.printf("    60 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", cf4.getPointEstimate(), cf3.getVariance(),
				cf4.getZeta(), cf4.getCfMin(), cf4.getCfMax(), Math.abs(cf3.getZeta() / cf3.getPointEstimate()));
		System.out.printf("-------------------------------------------------------------------------------------\n");
	}
}
