package simModel;

import java.util.ArrayList;

class OutputBuffer {
	protected ArrayList<Integer> list = new ArrayList<Integer>();

	protected void spInsertQue(int sid) {
		list.add(sid);
	}

	protected int spRemoveQue() {
		int sid = list.remove(0);
		return sid;
	}
}
