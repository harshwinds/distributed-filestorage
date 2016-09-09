package harshwinds.distributedFileStorage;

import java.io.OutputStream;
import java.util.UUID;

public class DistributedFile {

	private String md5 = null;
	private UUID fileId = null;
	private int chunkSize = 0;
	private int numberOfChunks = 0;
	private OutputStream file = null;
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public UUID getFileId() {
		return fileId;
	}
	public void setFileId(UUID fileId) {
		this.fileId = fileId;
	}
	
	public int getChunkSize() {
		return chunkSize;
	}
	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	
	public int getNumberOfChunks() {
		return numberOfChunks;
	}
	public void setNumberOfChunks(int numberOfChunks) {
		this.numberOfChunks = numberOfChunks;
	}
	
	public OutputStream getFile() {
		return file;
	}
	public void setFile(OutputStream file) {
		this.file = file;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (!(o instanceof DistributedFile)) {
			return false;
		}
		
		DistributedFile that = (DistributedFile) o;
		return this.md5 == null ? that.md5 == null : this.md5.equals(that.md5) &&
				this.fileId == null ? that.fileId == null : this.fileId.equals(that.fileId);
	}
	
	@Override
	public int hashCode() {
		return (this.md5 == null ? 42 : this.md5.hashCode() * 42) +
				(this.fileId == null ? 42 : this.fileId.hashCode() * 42);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(md5)
				.append("|")
				.append(fileId)
				.append("|")
				.append(chunkSize)
				.append("|")
				.append(numberOfChunks);
		
		return sb.toString();
	}
}
