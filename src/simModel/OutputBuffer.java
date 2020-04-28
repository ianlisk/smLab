package simModel;

import java.util.ArrayList;

class OutputBuffer {
	protected ArrayList<Integer> list = new ArrayList<>();

	protected void insert(int sid) {
		list.add(sid);
	}

	protected int remove() {
		int sid = list.remove(0);
		return sid;
	}
}
