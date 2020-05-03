package simModel;

import java.util.ArrayList;

class NewSampleBuffer {
	protected ArrayList<Sample> list = new ArrayList<>();

	// UDPs
	protected void spInsertQue(Sample icSample) {
		list.add(icSample);
	}

	// protected void addHead(Sample icSample) {
	// list.add(0, icSample);
	// }

	protected Sample spRemoveQue() {
		Sample icSample = list.remove(0);
		return icSample;
	}

	int getN() {
		return list.size();
	}

	protected void insertRushSample(Sample icSample) {
		// get(i) ArrayList<Sample>;
		int len = getN();
		Sample sample;
		int index = 0;
		for (int i = 0; i < len; i++) {
			sample = list.get(i);
			if (sample.uRush == false) {
				index = i;
				break;
			}
		}
		list.add(index, icSample);
	}

}
