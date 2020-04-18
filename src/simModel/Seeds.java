package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds {
	int[] interArrSeeds;
	int cleanTimeSeed;
	int rushSampleSeed;
	int loadUnloadTime;
	int[] timeToFailureSeeds;
	int[] repairTime;
	int sequenceSeed;
	
	public Seeds(RandomSeedGenerator rsg)
	{		
		cleanTimeSeed  = rsg.nextSeed();
		rushSampleSeed = rsg.nextSeed();
		cycleTimeSeed  = rsg.nextSeed();
		sequenceSeed   = rsg.nextSeed();
		
		interArrSeeds = new int[24];
		for (int i = 0; i < 24; i++) { interArrSeeds[i] = rsg.nextSeed(); }
		
		timeToFailureSeeds = new int[5];
		for (int i = 0; i < 5; i++) { timeToFailureSeeds[i] = rsg.nextSeed(); }
		
		fixTimeSeeds = new int[5];
		for (int i = 0; i < 5; i++) { fixTimeSeeds[i] = rsg.nextSeed(); }		
	}
}
