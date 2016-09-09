package harshwinds.distributedFileStorage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import harshwinds.distributedFileStorage.storage.DistributedCache;
import harshwinds.distributedFileStorage.util.MD5Utils;

public class FileStorageManager {
	private DistributedCache dc = null;
	
	public FileStorageManager(DistributedCache dc) throws NoSuchAlgorithmException {
		this.dc = dc;
	}
	
	public DistributedFile store(InputStream file) throws Exception {
		DistributedFile df = new DistributedFile();
    	df.setFileId(UUID.randomUUID());
    	df.setChunkSize(dc.getMaxChunkSize());
		
		int chunkCount = 0;
		
		byte[] chunk = new byte[df.getChunkSize()];
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		try (BufferedInputStream bis = new BufferedInputStream(file);
			 DigestInputStream dis = new DigestInputStream(bis, md)) {
            while (dis.read(chunk) > 0) {
            	dc.store(df.getFileId(), chunkCount++, chunk);
            }
        }
        
		df.setMd5(MD5Utils.getMD5Checksum(md.digest()));
		df.setNumberOfChunks(chunkCount);
    	
    	return df;
	}
	
	public DistributedFile retrieve(UUID fileId) throws Exception {
		DistributedFile df = new DistributedFile();
		df.setFileId(fileId);
		df.setChunkSize(dc.getMaxChunkSize());
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		int chunkCount = 0;
		
		try (ByteArrayOutputStream mergingStream = new ByteArrayOutputStream();
			DigestOutputStream dos = new DigestOutputStream(mergingStream, md)) {
			
			byte[] chunk = null;
			do {
	        	chunk = dc.retrieve(fileId, chunkCount++);
	        	if (chunk == null) {
	        		chunkCount--; // Went one too far
	        	} else {
	        		dos.write(chunk);
	        	}
	        } while (chunk != null && chunk.length == df.getChunkSize());
	        
	        df.setMd5(MD5Utils.getMD5Checksum(md.digest()));
	        df.setNumberOfChunks(chunkCount);
	        df.setFile(mergingStream);
		}
		
		return df;
	}

}
