package simModel;

import java.util.ArrayList;

class OutputBuffer
{
	protected ArrayList<Integer> list = new ArrayList<>();
		
	/* 
	 * TODO 我们paper中没有OutputBuffer udp这部分
	 * UPDs */
	protected void spInsertQueue(int sid) {
		list.add(sid);
	}
	
	protected int spRemoveQueue() {		
		int sid = list.remove(0);
		return sid;
	}
}
