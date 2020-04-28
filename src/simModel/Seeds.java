package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds {
	int arr;
	int cleanTimeSeed;
	int rushSampleSeed;
	int loadUnloadTime;
	int timeToFailureSeeds;
	int repairTime;
	int sequenceSeed;
	int cycleTimeSeed;
	int[] fixTimeSeeds;

	public Seeds(RandomSeedGenerator rsg) {
		arr = rsg.nextSeed();
		cleanTimeSeed = rsg.nextSeed();
		rushSampleSeed = rsg.nextSeed();
		cycleTimeSeed = rsg.nextSeed();
		sequenceSeed = rsg.nextSeed();
		repairTime = rsg.nextSeed();

		// timeToFailureSeeds = new int[5];
		// for (int i = 0; i < 5; i++) {
		// timeToFailureSeeds[i] = rsg.nextSeed();
		// }

		fixTimeSeeds = new int[5];
		for (int i = 0; i < 5; i++) {
			fixTimeSeeds[i] = rsg.nextSeed();
		}
	}
}
