package harshwinds.distributedFileStorage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.logging.Logger;

import harshwinds.distributedFileStorage.storage.DistributedCache;
import harshwinds.distributedFileStorage.storage.MemcachedDistributedCache;

public class DistributedFileStorageTestApp {
	
	private static final Logger LOG = Logger.getLogger(DistributedFileStorageTestApp.class.getName());

	public static void main(String[] args) {
		LOG.fine("Starting MemcachedTest...");
		
		FileStorageManager fsm = null;
		try {
			DistributedCache dc = new MemcachedDistributedCache(Arrays.asList("192.168.99.100:9001"));
			fsm = new FileStorageManager(dc);
		} catch (Exception e) {
			LOG.severe("Failed to create distributed cache: " + e.getMessage());
			System.exit(1);
		}
        
        
        if (fsm == null) {
        	LOG.severe("Failed to create file storage manager.  Aborting.");
        	System.exit(1);
        }
        
        File file = new File("./../bigoldfile.dat");
        DistributedFile storedFile = null;
        try {
			storedFile = fsm.store(new FileInputStream(file));
			LOG.info("Stored: " + storedFile);
		} catch (Exception e) {
			LOG.severe("Failed to store file: " + e.getMessage());
		}
        
        DistributedFile retrievedFile = null;
        try {
        	retrievedFile = fsm.retrieve(storedFile.getFileId());
        	LOG.info("Retrieved: " + retrievedFile);
        } catch (Exception e) {
        	LOG.severe("Failed to retrieve file: " + e.getMessage());
        }
        
        if (storedFile.getMd5().equals(retrievedFile.getMd5())) {
        	LOG.info("Retrieved file is the same as the stored file.");
        } else {
        	LOG.info("Oops.  Retrieved file is corrupted.");
        }
        
        LOG.fine("...MemcachedTest complete.");
	}
}
