package simModel;

import java.util.ArrayList;

class InputBuffer {
	protected ArrayList<Integer> list = new ArrayList<>();
	protected int length;

	/* 
	 * TODO 我们paper中没有InputBuffer udp这部分
	 * UDPs */
	protected void spInsertQueue(int shid) {
		list.add(shid);
	}

	protected int spRemoveQueue() {
		int sid = list.remove(0);
		return sid;
	}

}
