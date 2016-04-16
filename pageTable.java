package vmmanager;
import vmsimulation.*;

import java.util.*;

public class pageTable {
	
	//update(), lookup(), isValid() 

	
	Map<Integer, Integer> table = new HashMap<Integer, Integer>();
	//used to retrieve key from value
	Map<Integer, Integer> invTable = new HashMap<Integer, Integer>();
	
	//public void add(int page, int frame){
		//table.put(page, frame);
		//invTable.put(frame, page);
	//}
	public boolean contains(int page){
		
		return table.get(page)!=null;
	}
	public int get(int page){
		
		return table.get(page);
	}
	public int getInvert(int frame){
		
		return invTable.get(frame);
	}
	public void remove(int page){
		
		invTable.remove(table.get(page));//removes a frame as key
		table.remove(page);//removes a page as key
	}
	public void removeFrame(int frame){
		
		table.remove(invTable.get(frame));
		invTable.remove(frame);
	}
	public void add(int pageNumber, int frameNumber) {
		// TODO Auto-generated method stub
		
	}
}
