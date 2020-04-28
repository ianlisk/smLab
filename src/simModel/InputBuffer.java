package simModel;

import java.util.ArrayList;

class InputBuffer {
	protected ArrayList<Integer> list = new ArrayList<>();
	protected int capacity;
	
	//user define procedures
	protected void insert(int sid) {
		list.add(sid);
	}
	
	protected int remove() {
		int Sid = list.remove(0);
		return Sid;
	}
	
	protected boolean isAvailable() {  
		return list.size() < capacity;
	}
	

}
