package vmmanager;
import vmsimulation.*;

/*Author: Landon Soriano
 */

public class VirtualMemoryManagerV0 {

	MainMemory memory;
	BackingStore disk;
	int pageSize,binSize;

	public VirtualMemoryManagerV0(MainMemory memory, BackingStore disk, Integer pageSize) throws MemoryException {
    	this.memory = memory;
    	this.disk = disk;
    	this.pageSize = pageSize;
    	//the number of binary digits, used when converting int to bits
    	binSize = 30 - BitwiseToolbox.getBitString(memory.size(), 31).indexOf('1');




	}

  	/**
  	 * Writes a value to a byte at a given address -
  	 * To be implemented in versions V0 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
   	public void writeByte(Integer fourByteBinaryString, Byte value) throws MemoryException {
		// TO IMPLEMENT: V0, V1, V2, V3, V4
		memory.writeByte(fourByteBinaryString, value);
        System.out.println("RAM write: " + BitwiseToolbox.getBitString(fourByteBinaryString, binSize) + " <-- " + value);
  		return;
  	}

  	/**
  	 * Reads a byte from a given address -
  	 * To be implemented in versions V0 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
   	public Byte readByte(Integer fourByteBinaryString) throws MemoryException {
		byte value = 0;
		// TO IMPLEMENT: V0, V1, V2, V3, V4
		memory.writeByte(fourByteBinaryString, value);
        System.out.println("RAM write: " + BitwiseToolbox.getBitString(fourByteBinaryString, binSize) + " <-- " + value);
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
		//Fill out later

  		return;
  	}

  	/**
  	 * Writes all pages currently in memory frames back to disk -
  	 * To be implemented in versions V1 and above.
  	 *
  	 * @throws MemoryException If there is an invalid access
  	 */
  	public void writeBackAllPagesToDisk() throws MemoryException {
		//Fill out later
  	}

  	/**
  	 * Returns the number of page faults that have occurred to date -
  	 * To be implemented in versions V1 and above.
  	 *
  	 * @return Number of page faults
  	 */
  	public int getPageFaultCount() {
		int count = 0;
		//Fill out later
  		return count;
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
		//Fill out later
  		return count;
  	}
}
