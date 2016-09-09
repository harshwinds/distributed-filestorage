package harshwinds.distributedFileStorage.storage;

import java.util.UUID;

public interface DistributedCache {

	int getMaxChunkSize();
	
	void store(UUID fileId, int chunkNumber, byte[] chunk);
	 
	byte[] retrieve(UUID fileId, int chunkNumber);
}
