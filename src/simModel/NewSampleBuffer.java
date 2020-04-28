package simModel;

import java.util.ArrayList;

class NewSampleBuffer {
	protected ArrayList<Sample> list = new ArrayList<>();

	// UDPs
	protected void addTail(Sample icSample) {
		list.add(icSample);
	}

	protected void addHead(Sample icSample) {
		list.add(0, icSample);
	}

	protected Sample removeHead() {
		Sample icSample = list.remove(0);
		return icSample;
	}
}
