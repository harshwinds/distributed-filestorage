package harshwinds.distributedFileStorage.storage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;

public class MemcachedDistributedCache implements DistributedCache {
	
	private MemcachedClient mc = null;
	
	public MemcachedDistributedCache(List<String> servers) throws IOException {
		mc = new MemcachedClient(
        		new ConnectionFactoryBuilder()
        				.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
        				.build(),
        		AddrUtil.getAddresses(servers));
	}
	
	@Override
	public int getMaxChunkSize() {
		return 1000 * 1024; // less than 1 MB for Memcached
	}

	@Override
	public void store(UUID fileId, int chunkNumber, byte[] chunk) {
		mc.set(fileId.toString() + "-" + chunkNumber, 0, chunk); // Not worried about checking result of set for this exercise
	}

	@Override
	public byte[] retrieve(UUID fileId, int chunkNumber) {
		Object result = mc.get(fileId.toString() + "-" + chunkNumber);
		if (result instanceof byte[]) {
			return (byte[]) result;
		}
		
		return null;
	}

}
