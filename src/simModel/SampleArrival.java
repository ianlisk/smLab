package simModel;

import simulationModelling.ScheduledAction;

/**
 * Describe: 
 * 
 * @since JDK 1.8
 * @author Yang Gao 300128708
 * @Email ygao151@uottawa.ca
 * @Date: Apr 15, 2020 8:42:19 PM
 */
class SampleArrival extends ScheduledAction 
{
	SMLabTesting model;  // reference to model object
	public SampleArrival(SMLabTesting model) { this.model = model; }


	public void actionEvent() 
	{
		// WArrival Action Sequence SCS
	     Sample icSample = new Sample();
	     icSample.uType = model.rvp.uCustomerType();
	     icSample.startWaitTime = model.getClock(); 
	     model.qCustLine.add(icSample);
	}


	/* (non-Javadoc)
	 * @see simulationModelling.ScheduledAction#timeSequence()
	 */
	@Override
	protected double timeSequence() {
		// TODO Auto-generated method stub
		 return 0;
	}


}
