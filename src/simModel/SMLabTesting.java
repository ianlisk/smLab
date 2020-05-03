package simModel;

import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;

public class SMLabTesting extends AOSimulationModel {

	// Constants available from Constants class
	/* Parameter */
	// Define the parameters
	public int[] numTesters = new int[5];
	public int numSampleHolders;
	public LoadUnloadDevice.logicType rLoadUnloadDeviceLogicConfiguration;

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action
	// protected ArrayList<Sample> qNewSample = new ArrayList<Sample>();
	protected InputBuffer[] qInputBuffer = new InputBuffer[6];
	OutputBuffer[] qOutputBuffer = new OutputBuffer[6];
	Tester[][] rcTester = new Tester[Constants.NUM_TESTERS_IN_CELL][];
	SampleHolder[] rcSampleHolder;
	LoadUnloadDevice rLoadUnloadDevice = new LoadUnloadDevice();
	TransportationLoop rqTransportationLoop = new TransportationLoop();
	NewSampleBuffer qNewSampleBuffer = new NewSampleBuffer();

	/* Input Variables */
	// Define any Independent Input Varaibles here

	// References to RVP and DVP objects
	protected RVPs rvp; // Reference to rvp object - object
						// created in constructor
	// protected DVPs dvp = new DVPs(this); // Reference to dvp object
	// protected UDPs udp = new UDPs(this);
	protected Testing testing = new Testing();
	// Output object
	protected Output output = new Output(this);

	// Output values - define the public methods that return values
	// required for experimentation.

	// For validation log display use
	public int getTotalSample() {
		return output.getTotalSample();
	}

	public int getOvertimedSample() {
		return output.getOvertimedSample();
	}

	public int getNumPassedSample() {
		return output.getNumPassedSample();
	}

	public double getTurnaroundUnsatisfiedLevel() {
		return output.getTurnaroundUnsatisfiedLevel();
	}

	public double[] getOccupyingRateOfBuffer() {
		return output.getOccupyingRateOfBuffer();
	}

	// Constructor
	public SMLabTesting(double t0time, double tftime, int[] numTesters, int numSampleHolders,
			LoadUnloadDevice.logicType logicConfiguration, Seeds sd, boolean log) {
		// Initialise parameters here
		this.numTesters = numTesters;
		this.numSampleHolders = numSampleHolders;
		this.rLoadUnloadDeviceLogicConfiguration = logicConfiguration;
		// For turning on logging
		logFlag = log;
		initialiseClasses(sd);
		// Initialise the simulation model
		initAOSimulModel(t0time, tftime);

		// Schedule the first arrivals
		Initialise init = new Initialise(this);
		scheduleAction(init); // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
		SampleArrival aSampleArr = new SampleArrival();
		scheduleAction(aSampleArr); // Start SampleArrival
		Move seqAct = new Move();
		seqAct.startingEvent();
		scheduleActivity(seqAct);// Start Move
	}

	private void initialiseClasses(Seeds sd) {
		DVPs.model = this;
		Initialise.model = this;
		RepairClean.model = this;
		Move.model = this;
		Output.model = this;
		RVPs.model = this;
		SampleArrival.model = this;
		Testing.model = this;
		LogPrinter.model = this;
		LoadUnload.model = this;
		SampleArrival.initRvps(sd);
		LoadUnload.initRvps(sd);
		RepairClean.initRvps(sd);
		// Create RVP object with given seed
		rvp = new RVPs(sd);
	}

	/************ Implementation of Data Modules ***********/
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj) {
		reschedule(behObj);
		// Check preconditions of Conditional Activities
		while (scanPreconditions() == true)
			;
	}

	// Single scan of all preconditions
	// Returns true if at least one precondition was true.
	private boolean scanPreconditions() {
		boolean statusChanged = false;
		// Conditional Actions
		if (LoadUnload.precondition() == true) {
			LoadUnload act = new LoadUnload();
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if (Testing.precondition() == true) {
			Testing act = new Testing();
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if (RepairClean.precondition() == true) {
			RepairClean act = new RepairClean();
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		return (statusChanged);
	}

	/*
	 * public void eventOccured() { LogPrinter.print(); this.showSBL(); // Can
	 * add other debug code to monitor the status of the system // See examples
	 * for suggestions on setup logging
	 * 
	 * // Setup an updateTrjSequences() method in the Output class // and call
	 * here if you have Trajectory Sets // updateTrjSequences() }
	 */

	public void eventOccured() {
		if (logFlag)
			printDebug();
	}

	// for Debugging
	boolean logFlag = false;

	protected void printDebug() {
		// Debugging
		System.out.println(">-----------------------------------------------<");
		System.out.printf("Clock = %10.4f\n", getClock());
		LogPrinter.print();
		showSBL();
		System.out.println(">-----------------------------------------------<");
	}

}
