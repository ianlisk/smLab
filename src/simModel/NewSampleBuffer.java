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

	protected void insertRushSample(Sample rushSample) {
		int len = getN();
		int index = 0;
		for (int i = 0; i < len; i++) {
			if (list.get(i).uRush == false) {
				index = i;
				break;
			}
		}
		list.add(index, rushSample);
	}

}
