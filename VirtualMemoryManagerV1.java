package vmmanager;
import vmsimulation.*;

/*Author: Landon Soriano
 */

public class VirtualMemoryManagerV1 {
	MainMemory memory;
	BackingStore disk;
	int pageSize,binSize;

	int pageInDisk,cMemorySize = 0,pfCounter = 0,bCounter = 0;
	pageTable table;

	public VirtualMemoryManagerV1(MainMemory memory, BackingStore disk, Integer pageSize) throws MemoryException {
    	this.memory = memory;
    	this.disk = disk;
    	this.pageSize = pageSize;
    	//the number of binary digits, used when converting int to bits
    	binSize = 30 - BitwiseToolbox.getBitString(memory.size(), 31).indexOf('1');
    	table = new pageTable();





	}

  	/**
  	 * Writes a value to a byte at a given address -
  	 * To be implemented in versions V0 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
   	public void writeByte(Integer fourByteBinaryString, Byte value) throws MemoryException {
    	int pageNumber,offset;
    	//the page# of the address in disk
    	pageNumber = fourByteBinaryString/pageSize;
    	offset = fourByteBinaryString - pageNumber*pageSize;

    	//if given address doesn't exit in pagetable, load corresponding page into memory
    	if(!table.contains(pageNumber)){
    		pfCounter++;
    		System.out.println("Bringing page " + pageNumber + " into frame " + getFrameNumber());
    		table.add(pageNumber, getFrameNumber());
    		this.loadPage(disk.readPage(pageNumber));
    	}
    	else{
    		System.out.println("Page " + pageNumber + " is in memory");
    	}

		memory.writeByte(table.get(pageNumber)*pageSize+offset, value);
        System.out.println("RAM: " + BitwiseToolbox.getBitString(table.get(pageNumber)*pageSize+offset,binSize) + " <-- " + value);
  	}

  	/**
  	 * Reads a byte from a given address -
  	 * To be implemented in versions V0 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
   	public Byte readByte(Integer fourByteBinaryString) throws MemoryException {
    	int pageNumber,offset;
    	//the page# of the address in disk
    	pageNumber = fourByteBinaryString/pageSize;
    	offset = fourByteBinaryString - pageNumber*pageSize;

    	//if given address doesn't exit in pagetable, load corresponding page into memory
    	if(!table.contains(pageNumber)){
    		pfCounter++;
    		System.out.println("Bringing page " + pageNumber + " into frame " + getFrameNumber());
    		//System.out.println("adding page "+pageNumber + " frame "+this.getFrameNumber()+" to table");
    		table.add(pageNumber, getFrameNumber());
    		this.loadPage(disk.readPage(pageNumber));
    	}
    	else{
    		System.out.println("Page " + pageNumber + " is in memory");
    	}
    	//read byte from memory using pagetable
        byte value = memory.readByte(table.get(pageNumber)*pageSize+offset);
        System.out.println("RAM: " + BitwiseToolbox.getBitString(table.get(pageNumber)*pageSize+offset, binSize)+ " --> " + value );
        return value;
  	}

  	/**
  	 * Prints all memory content -
  	 * To be implemented in versions V0 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
   	public void printMemoryContent() throws MemoryException {

        for(int x = 0; x < memory.size();x++){
        	System.out.println(BitwiseToolbox.getBitString(x, binSize) + ": " + memory.readByte(x));
        }
        return;
  	}

  	/**
  	 * Prints all disk content, structured by pages -
  	 * To be implemented in versions V1 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
   	public void printDiskContent() throws MemoryException {
   		//the number of pages in disk
    	pageInDisk = disk.size()/pageSize;
        for(int x = 0 ; x < pageInDisk; x++){
        	System.out.print("PAGE " + x + ": ");
        	for(int z = 0; z < disk.readPage(x).length; z++){
        		if(z+1 != disk.readPage(x).length){
        			System.out.print(disk.readPage(x)[z] + ",");
        		}
        		else{
        			System.out.print(disk.readPage(x)[z]);
        		}
        	}
        	System.out.println();
        }
  	}

  	/**
  	 * Writes all pages currently in memory frames back to disk -
  	 * To be implemented in versions V1 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
  	public void writeBackAllPagesToDisk() throws MemoryException {
  		// TO IMPLEMENT: V1, V2, V3, V4
  		//*
    	//System.out.println("WRITING BACK TO DISK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	//System.out.println("CURRENT MEMORY SIZE IS :" + this.cMemorySize);
    	//System.out.println("MEMORY SIZE IS " + memory.size());
        for(int x = 0; x < this.cMemorySize ; x+= pageSize){
    		int currentFrame = x / pageSize;
    		byte[] temp = new byte[pageSize];
    		int c = 0;
    		for(int z = x ; z < x + pageSize; z++){
    			temp[c] = memory.readByte(z);
    			bCounter++;
    			c++;
    		}
    		disk.writePage(table.getInvert(currentFrame), temp);
    	}

  	}

  	/**
  	 * Returns the number of page faults that have occurred to date -
  	 * To be implemented in versions V1 and above.
  	 *
  	 * @return Number of page faults
  	 */
  	public int getPageFaultCount() {
		int count = 0;
  		return pfCounter;
  	}

  	/**
  	 * Returns the number of bytes that have been transfered back and forth between the memory
  	 * and the disk to date -
  	 * To be implemented in versions V1 and above.
  	 *
  	 * @return Number of bytes transferred
  	 */
  	public int getTransferedByteCount() {
		int count = 0;
  		return bCounter;
  	}


  	 /**
     * loads a given byte array into memory
     * @param page The byte array to be loaded
     * @throws MemoryException
     */
    private void loadPage(byte[] page) throws MemoryException{
    	for(int x = 0; x < page.length; x++){
    		//start the write from current memory size
    		memory.writeByte(cMemorySize, page[x]);
    		bCounter++;
    		cMemorySize++;
    	}
    }
    private int getFrameNumber(){
    	return (cMemorySize)/pageSize;
    }
}
