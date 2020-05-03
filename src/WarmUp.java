import cern.jet.random.engine.RandomSeedGenerator;
import outputAnalysis.WelchAverage;
import simModel.Constants;
import simModel.LoadUnloadDevice;
import simModel.SMLabTesting;
import simModel.Seeds;

class WarmUp {
	private static SMLabTesting model;
	private static double[][] totalUnsatisfiedLevelOutPut;

	public static void main(String[] args) {
		int NUMRUNS = 10;
		Seeds[] sds = new Seeds[NUMRUNS];
		double intervalStart, intervalEnd; // start and end times of current
											// interval
		double intervalLength = 60; // 1 hour intervals
		int numIntervals = 24 * 50; // Total 50 days observation interval
		totalUnsatisfiedLevelOutPut = new double[NUMRUNS][numIntervals];

		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (int i = 0; i < NUMRUNS; i++)
			sds[i] = new Seeds(rsg);

		System.out.println("Base Case addBuffer = false; addNumPallets = 0; numInputRelease = 0");
		for (int i = 0; i < NUMRUNS; i++) {
			model = new SMLabTesting(0.0, intervalLength * numIntervals, new int[] { 10, 10, 10, 10, 10 },
					Constants.INIT_NUM_SAMPLE_HOLDERS, LoadUnloadDevice.logicType.CURRENT_LOGIC, sds[i], false);

			for (int interval = 0; interval < numIntervals; interval++) {
				intervalStart = interval * intervalLength;
				intervalEnd = intervalStart + intervalLength;
				model.setTimef(intervalEnd);
				model.runSimulation();
				totalUnsatisfiedLevelOutPut[i][interval] = model.getTurnaroundUnsatisfiedLevel();
			}
		}
		int[] wSizeTotalLostCost = { 0, 1, 3, 5 };
		WelchAverage welchTotalUnsatisfiedLevel = new WelchAverage(totalUnsatisfiedLevelOutPut, wSizeTotalLostCost);
		System.out.println("Total Number of Turnaround Unsatisfied Level");
		printWelchOutputMatrix(welchTotalUnsatisfiedLevel.getWelchAvgOutput(), wSizeTotalLostCost, 1);
	}

	private static void printWelchOutputMatrix(double[][] m, int[] w, double intervalLength) {
		int ix, jx;
		System.out.print("t,");
		for (ix = 0; ix < w.length - 1; ix++)
			System.out.print("w = " + w[ix] + ",");
		System.out.println("w = " + w[ix]);
		for (jx = 0; jx < m[0].length; jx++) {
			System.out.print(((jx + 1) * intervalLength) + ", ");
			for (ix = 0; ix < m.length; ix++) {
				if (jx < m[ix].length)
					System.out.print(m[ix][jx]);
				if (ix != m.length - 1 && jx < m[ix + 1].length)
					System.out.print(", ");
				else if (jx < m[ix].length)
					System.out.println();
			}
		}
	}
}
