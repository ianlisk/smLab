package simModel;

import java.util.ArrayList;

class InputBuffer {
	protected ArrayList<Integer> list = new ArrayList<>();
	protected int capacity;

	// user define procedures
	protected void spInsertQue(int sid) {
		list.add(sid);
	}

	protected int spRemoveQue() {
		int Sid = list.remove(0);
		return Sid;
	}

	protected boolean isAvailable() {
		return list.size() < capacity;
	}

	protected int getN() {
		return list.size();
	}

}
