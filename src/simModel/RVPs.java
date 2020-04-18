package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.Uniform;
import dataModelling.TriangularVariate;
import cern.jet.random.engine.MersenneTwister;

class RVPs {
	static SMLabTesting model; // for accessing the clock
	// Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define
	// reference variables here and create the objects in the
	// constructor with seeds

	// Constructor
	protected RVPs(Seeds sd) {
		sampleArrDist = new Exponential(1.0 / arrMean, new MersenneTwister(sd.sampleArr));
		rushSampleDist = new Uniform(0.0, 1.0, new MersenneTwister(sd.rushSample));
		sequenceTypeDist = new Uniform(0.0, 100.0, new MersenneTwister(sd.sequenceType));
		timeToFailureDist = new Exponential((1.0 / failureMean), new MersenneTwister(sd.timeToFailure));
		cleanTimeDist = new TriangularVariate(5.0, 6.0, 10.0, new MersenneTwister(sd.cleanTime));
		for(int cid : Constants.DEFAULT_CID_ARRAY){
			repairTimeDist[cid] = new Exponential(1.0 / repairTimeMean, new MersenneTwister(sd.repairTime[cid]));
		}
		
		loadUnloadTimeDist = new TriangularVariate(0.18, 0.23, 0.45, new MersenneTwister(sd.loadUnloadTime));
	}

	private Exponential sampleArrDist;

	protected // mean//
	protected double duSampleArr() {
		/*
		 * kojo interarr double nxtArrival; double mean; if(model.getClock() <
		 * 90) mean = MEAN1; else if (model.getClock() < 210) mean = MEAN2; else
		 * if (model.getClock() < 420) mean = MEAN3; else if (model.getClock() <
		 * 540) mean = MEAN4; else mean = MEAN5; nxtArrival =
		 * model.getClock()+interArrDist.nextDouble(1.0/mean); if(nxtArrival >
		 * model.closingTime) nxtArrival = -1.0; // Ends time sequence
		 * return(nxtArrival);
		 */

	}

	private Uniform rushSampleDist;

	protected boolean duRush() {
		return rushSampleDist.nextDouble() < 0.07;
	}

	private Uniform sequenceTypeDist;

	protected Integer[] uGetSequenceType() {
		double value = sequenceTypeDist.nextDouble();
		Integer[] sequence;

		if (value < 9) {
			sequence = new Integer[] { Constants.C1, Constants.C2, Constants.C4, Constants.C5, Constants.END };
		} else if (value < 22) {
			sequence = new Integer[] { Constants.C3, Constants.C4, Constants.C5, Constants.END };
		} else if (value < 37) {
			sequence = new Integer[] { Constants.C1, Constants.C2, Constants.C3, Constants.C4, Constants.END };
		} else if (value < 49) {
			sequence = new Integer[] { Constants.C4, Constants.C3, Constants.C2, Constants.END };
		} else if (value < 56) {
			sequence = new Integer[] { Constants.C2, Constants.C5, Constants.C1, Constants.END };
		} else if (value < 67) {
			sequence = new Integer[] { Constants.C4, Constants.C5, Constants.C2, Constants.C3, Constants.END };
		} else if (value < 81) {
			sequence = new Integer[] { Constants.C1, Constants.C5, Constants.C3, Constants.C4, Constants.END };
		} else if (value < 87) {
			sequence = new Integer[] { Constants.C5, Constants.C3, Constants.C1, Constants.END };
		} else {
			sequence = new Integer[] { Constants.C2, Constants.C4, Constants.C5, Constants.END };
		}
		return sequence;
	}

	private Exponential timeToFailureDist;
	protected double[] failureMean = new double[] { 840, 540, 900, 960 };// 去年840和540中间加了-1，且去年是840/60*3600//

	protected double uTimeToFailure(ine cid) {
		return timeToFailureDist[cid].nextDouble();
	}

	private TriangularVariate cleanTimeDist;

	protected double duCleanTime() {
		return cleanTimeDist.next();
	}

	private Exponential[] repairTimeDist;
	protected double[] fixTimeMeans = new double[] { 11, 7, 14 };// 去年11*60，
																	// 且11和7中间加了-1//

	protected double uRepairTime(int cid) {
		return repairTimeDist[cid].nextDouble();
	}

	protected double uRepairCleanTime(int cid){
		if ( int cid == 2 ) {
			uRepairCleanTime(cid) = duCleanTime();
		} else {
			uRepairCleanTime(cid) = tester[cid][tid].timeToFailure + uRepairTime(cid);
		}
		return uRepairCleanTime(cid)
	}

	private TriangularVariate loadUnloadTimeDist;

	protected double duloadUnloadTime(){
		return loadUnloadTimeDist.next();
	}
