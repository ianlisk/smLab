package simModel;

import java.util.ArrayList;

import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;

public class SMLabTesting extends AOSimulationModel {
	
	public void SMLabTesting(){
		
	}
	// Constants available from Constants class
	/* Parameter */
	// Define the parameters
	public int[] numTesters = new int[5];
	public int numSampleHolders;
	public boolean logicConfiguration;

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action
	protected ArrayList<Sample> qNewSample = new ArrayList<Sample>();
	protected InputBuffer[] qInputBuffer = new InputBuffer[6];
    OutputBuffer[] qOutputBuffer = new OutputBuffer[6];
	Tester[][] rcTester = new Tester[5][];
	SampleHolder[] rSampleHolder;

	/* Input Variables */
	// Define any Independent Input Varaibles here

	// References to RVP and DVP objects
	protected RVPs rvp; // Reference to rvp object - object created in
						// constructor
	protected DVPs dvp = new DVPs(this); // Reference to dvp object
	protected UDPs udp = new UDPs(this);

	// Output object
	protected Output output = new Output(this);

	// Output values - define the public methods that return values
	// required for experimentation.
	public int getTotalSample() {
		return output.getTotalSample();
	}

	public int getOvertimedSample() {
		return output.getOvertimedSample();
	}

	public double getTurnaroundUnsatisfiedLevel() {
		return output.getTurnaroundUnsatisfiedLevel();
	}

	// Constructor
	public SMLabTesting(double t0time, double tftime, int[] numTesters, int numSampleHolders,
			Boolean logicConfiguration, Seeds sd) {
		// Initialise parameters here
		this.numTesters = numTesters;
		this.numSampleHolders = numSampleHolders;
		this.logicConfiguration = logicConfiguration;

		// Create RVP object with given seed
//		rvp = new RVPs(this, sd);

		// rgCounter and qCustLine objects created in Initalise Action

		// Initialise the simulation model
		initAOSimulModel(t0time, tftime); // TODO initAOSimulModel(t0time,
											// t0time + 7200); // steady-state
											// study

		// Schedule the first arrivals
		Initialise init = new Initialise(this);
		scheduleAction(init); // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
		SampleArrival aSampleArr = new SampleArrival(this);
		scheduleAction(aSampleArr); // Start SampleArrival
		Move seqAct = new Move();
		seqAct.startingEvent();
		scheduleActivity(seqAct); // Start Move 
	}

	/**
	 * 
	 */
	public SMLabTesting() {
		 // TODO Auto-generated constructor stub
	}

	/************ Implementation of Data Modules ***********/
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj) {
		reschedule(behObj);
		// Check preconditions of Conditional Activities
		if (Testing.precondition() == true) {
			Testing act = new Testing();
			act.startingEvent();
			scheduleActivity(act);
		}
		// Check preconditions of Interruptions in Extended Activities
	}

	public void eventOccured() {
		// this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences()
	}

	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct) {
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}

}
