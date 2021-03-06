package Common;

import java.security.MessageDigest;

public class Encrypt {

	private static String hashString(String message, String algorithm)
	        {
	 
	    try {
	        MessageDigest digest = MessageDigest.getInstance(algorithm);
	        byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
	 
	        return convertByteArrayToHexString(hashedBytes);
	    } catch ( Exception ex) {
	    	return "";
	    }
		
	}
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
	public static String generateMD5(String message) throws  Exception {
        return hashString(message, "MD5");
    }
 
    public static String generateSHA1(String message) throws Exception {
        return hashString(message, "SHA-1");
    }
 
    public static String generateSHA256(String message) throws Exception {
        return hashString(message, "SHA-256");
    }
}
