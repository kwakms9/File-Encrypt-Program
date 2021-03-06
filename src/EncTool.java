import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//The standard Java crypto libraries don't do CCM mode as default, 
// so we will need another provider.
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncTool {

	static String endate;
	static String inFile = "plainText.txt";
	static String outFile = "cipherText.enc";
	static private String hexKey = "3eafda76cd8b015641cb946708675423";
	static String keyStore;
	static String keyName;
	static boolean success = false;

	private static void encryptRSA() {
		try {
			// Get the public key from the keyStore and set up the
			// Cipher object
			PublicKey publicKey = getPubKey(keyStore, keyName);
			Cipher rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

			// Read the plainText
			System.out.println("Loading plaintext file: " + inFile);
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] plainText = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(plainText);

			// Generate a symmetric key to encrypt the data and
			// initiate the AES Cipher Object
			System.out.println("Generating AES key");
			KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
			Key aesKey = sKenGen.generateKey();
			Cipher aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);

			// Encrypt the symmetric AES key with the public RSA key
			System.out.println("Encrypting Data");
			byte[] encodedKey = rsaCipher.doFinal(aesKey.getEncoded());
			// Encrypt the plaintext with the AES key
			byte[] cipherText = aesCipher.doFinal(plainText);

			// Write the encrypted AES key and cipher text to the
			// file.
			System.out.println("Writting to file: " + outFile);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(encodedKey);
			outToFile.write(cipherText);

			System.out.println("Closing Files");
			rawDataFromFile.close();
			outToFile.close();
		} catch (Exception e) {
			System.out.println("Doh: " + e);
		}
	}

	/**
	 * Method to get the private key from the key store
	 * 
	 * @param keyStoreFile
	 *                key store file
	 * @param keyName
	 *                key name
	 * @return private key
	 */

	private static PrivateKey getPrivKey(String keyStoreFile, String keyName) {
		PrivateKey key = null;
		try {

			KeyStore myKeyStore = KeyStore.getInstance("JKS");
			FileInputStream inStream = new FileInputStream(keyStoreFile);

			// Get the keyStore password, using Console lets us mask
			// the password
			Console console = System.console();
			char[] password = console.readPassword("Enter your secret password: ");
			myKeyStore.load(inStream, password);

			key = (PrivateKey) myKeyStore.getKey(keyName, password);

		} catch (Exception e) {
			System.out.println("doh" + e);
		}
		return key;
	}

	private static PublicKey getPubKey(String keyStoreFile, String keyName) {
		PublicKey publicKey = null;
		try {
			// Load the keyStore
			KeyStore myKeyStore = KeyStore.getInstance("JKS");
			FileInputStream inStream = new FileInputStream(keyStoreFile);

			// Get the keyStore password, using Console lets us mask
			// the password
			Console console = System.console();
			char[] password = console.readPassword("Enter your secret password: ");
			myKeyStore.load(inStream, password);
			Certificate cert = myKeyStore.getCertificate(keyName);
			publicKey = cert.getPublicKey();
		} catch (Exception e) {
			System.out.println("Doh: " + e);
		}
		return publicKey;
	}

	private static void decryptRSA() {

		try {

			// Get the public key and initiate it with the Cipher
			// object

			PrivateKey privateKey = getPrivKey(keyStore, keyName);
			Cipher rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);

			// Read the plain text

			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] inBytes = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(inBytes);
			rawDataFromFile.close();

			byte[] keyBytes = new byte[256];
			for (int i = 0; i < 256; i++) {
				keyBytes[i] = inBytes[i];
			}

			byte[] decryptedKey = rsaCipher.doFinal(keyBytes);

			SecretKeySpec key = new SecretKeySpec(decryptedKey, "AES");

			Cipher aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.DECRYPT_MODE, key);

			byte[] cipherText = Arrays.copyOfRange(inBytes, 256, inBytes.length);

			byte[] original = aesCipher.doFinal(cipherText);

			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(original);
			outToFile.close();

		} catch (Exception e) {
			System.out.println("doh" + e);

		}
	}

	static void decryptAESCTRPrivate(String path,String prkey) {

		try {
			inFile = path;
			outFile = path;
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] inBytes = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(inBytes);
			rawDataFromFile.close();

			byte[] ivBytes = Arrays.copyOfRange(inBytes, 0, 16);

			SecretKeySpec key = new SecretKeySpec(hexStringToByteArray(prkey), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding");

			byte[] cipherText = Arrays.copyOfRange(inBytes, 16, inBytes.length + 1);

			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

			byte[] original = cipher.doFinal(cipherText);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(original);
			outToFile.close();

		} catch (Exception e) {
			System.out.println("doh " + e);
		}

	}
	
	static void decryptAESCTR(String inPath, String outPath) {

		try {
			inFile = inPath;
			outFile = outPath;
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] inBytes = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(inBytes);
			rawDataFromFile.close();

			byte[] ivBytes = Arrays.copyOfRange(inBytes, 0, 16);

			SecretKeySpec key = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding");

			byte[] cipherText = Arrays.copyOfRange(inBytes, 16, inBytes.length + 1);

			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

			byte[] original = cipher.doFinal(cipherText);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(original);
			outToFile.close();

		} catch (Exception e) {
			System.out.println("doh " + e);
		}

	}

	private static void decryptAESCCM() {

		try {

			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] inBytes = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(inBytes);
			rawDataFromFile.close();

			byte[] ivBytes = Arrays.copyOfRange(inBytes, 0, 10);
			System.out.println("Iv bytes");
			for (int i = 0; i < ivBytes.length; i++) {
				System.out.print(ivBytes[i] + ", ");
			}
			System.out.println();

			SecretKeySpec key = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CCM/NoPadding", new BouncyCastleProvider());

			byte[] cipherText = Arrays.copyOfRange(inBytes, 10, inBytes.length);

			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

			byte[] original = cipher.doFinal(cipherText);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(original);
			outToFile.close();

		} catch (Exception e) {
			System.out.println("doh " + e);

		}

	}

	private static void generateKey() {
		try {
			KeyGenerator sGen = KeyGenerator.getInstance("AES");
			Key aesKey = sGen.generateKey();
			System.out.println("Here are some bytes you can use as an AES key: "
					+ byteArrayToHexString(aesKey.getEncoded()));
		} catch (Exception e) {
			System.out.println("doh " + e);
		}
	}

	static void encryptAESCTRPrivate(String path,String prkey) {
		try {
			
			inFile = path;
			outFile = path;
			// Open and read the input file
			// N.B. this program reads the whole file into memory,
			// not good for large programs!
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] plainText = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(plainText);
			rawDataFromFile.close();

			// Set up the AES key & cipher object in CTR mode
			SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(prkey), "AES");
			Cipher encAESCTRcipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
			SecureRandom random = new SecureRandom();
			byte iv[] = new byte[16];
			random.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			encAESCTRcipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

			// Encrypt the data
			byte[] cipherText = encAESCTRcipher.doFinal(plainText);

			// Write file to disk
			System.out.println("Openning file to write: " + outFile);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(iv);
			outToFile.write(cipherText);
			outToFile.close();
			success = true;
			System.out.println(inFile + " encrypted as " + outFile);
		} catch (Exception e) {
			System.out.println("doh " + e);
			success = false;
		}
		
		
        
       
	
	}
	
	static void encryptAESCTR(String inPath, String outPath) {
		try {
			inFile = inPath;
			outFile = outPath;
			// Open and read the input file
			// N.B. this program reads the whole file into memory,
			// not good for large programs!
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] plainText = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(plainText);
			rawDataFromFile.close();

			// Set up the AES key & cipher object in CTR mode
			SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			Cipher encAESCTRcipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
			SecureRandom random = new SecureRandom();
			byte iv[] = new byte[16];
			random.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			encAESCTRcipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

			// Encrypt the data
			byte[] cipherText = encAESCTRcipher.doFinal(plainText);

			// Write file to disk
			System.out.println("Openning file to write: " + outFile);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(iv);
			outToFile.write(cipherText);
			outToFile.close();
			System.out.println(inFile + " encrypted as " + outFile);
		} catch (Exception e) {
			System.out.println("doh " + e);
		}
	}

	private static void encryptAESCCM() {
		try {
			// Open and read the input file
			// N.B. this program reads the whole file into memory,
			// not good for large programs!
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] plainText = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(plainText);
			rawDataFromFile.close();

			// Set up the AES key & cipher object in CCM mode
			SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			// Add a security provider that actually does provide
			// CCM mode
			Security.insertProviderAt(new BouncyCastleProvider(), 1);
			Cipher encAESCCMcipher = Cipher.getInstance("AES/CCM/NoPadding", "BC");
			SecureRandom random = new SecureRandom();
			byte iv[] = new byte[10]; // BC needs us to leave room
							// for the counter
			random.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			encAESCCMcipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

			// Encrypt the data
			byte[] cipherText = encAESCCMcipher.doFinal(plainText);

			// Write file to disk
			System.out.println("Openning file to write: " + outFile);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(iv);
			outToFile.write(cipherText);
			outToFile.close();
			System.out.println(inFile + " encrypted as " + outFile);
		} catch (Exception e) {
			System.out.println("doh " + e);
		}
	}

	// Code from
	// http://www.anyexample.com/programming/java/java%5Fsimple%5Fclass%5Fto%5Fcompute%5Fmd5%5Fhash.xml
	private static String byteArrayToHexString(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	// Code from http://javaconversions.blogspot.co.uk
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	public static String makenNewHexString(String id) {	//아이디마다 키값다르게 
		char hexArr[] = hexKey.toCharArray();
		int hexlen = hexArr.length;
		char idArr[] = id.toCharArray();
		String newKey="";
		String tmp ="";
		int i=0;
		
		for(i=0; i<idArr.length;i++) {	//문자들을 16진수로 변환
			tmp += Integer.toHexString(Character.getNumericValue(idArr[i]));
		}
		
		for(i=0;i<hexlen-tmp.length();i++) {	//key앞까지 채우기
			newKey+=hexArr[i];
		}		
		newKey+=tmp;	//key합치기
		return newKey;
	}
}