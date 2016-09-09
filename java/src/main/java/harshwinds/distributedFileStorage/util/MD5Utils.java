package harshwinds.distributedFileStorage.util;

public class MD5Utils {
	// See http://www.rgagnon.com/javadetails/java-0416.html
	public static String getMD5Checksum(byte[] md5) throws Exception {
	     String result = "";
	     for (int i=0; i < md5.length; i++) {
	    	 result += Integer.toString( ( md5[i] & 0xff ) + 0x100, 16).substring( 1 );
	     }
	     return result;
	}
}
