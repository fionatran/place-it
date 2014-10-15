package com.example.placeitmap;
/*
import java.util.Date;

import com.example.placeitmap.database.placeitDB;
import com.example.placeitmap.dto.Reminder;
import com.example.placeitmap.services.MyLocation;
import com.example.placeitmap.services.MyLocation.LocationResult;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class AddNewReminder extends Activity implements OnClickListener,LocationListener {
	 double longitude;
	   double latitude;
	   LocationManager lm;
		Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_reminder);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		 location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 LocationResult locationResult = new LocationResult(){
 		    @Override
 		    public void gotLocation(Location location){
 		        latitude = location.getLatitude();
 		        longitude = location.getLongitude();
 		    }
 		};
 		MyLocation myLocation = new MyLocation();
 		myLocation.getLocation(AddNewReminder.this, locationResult);
		Button onLeave = (Button) findViewById(R.id.button1);
		onLeave.setOnClickListener(this);
		
		
		Button onArrive = (Button) findViewById(R.id.button2);
		onArrive.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button1){
			Intent toNewLocation = new Intent(this,GoogleMaps.class);
			startActivity(toNewLocation);
		}
		else if(v.getId()==R.id.button2) {
			
			final View setView  = getLayoutInflater().inflate(R.layout.add_reminder, null);
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Add Reminder");
			alert.setView(setView); 
			alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            		Reminder rem = new Reminder();
	            		
	            		EditText reminderText = (EditText)setView.findViewById(R.id.editText1);
	            		EditText radius = (EditText)setView.findViewById(R.id.editText2);
	            		rem.setLatitude(latitude);
	            		rem.setLongitude(longitude);
	            		rem.setRemiderText(reminderText.getText().toString());
	            		rem.setRadius(Float.parseFloat(radius.getText().toString()));
	            		rem.setReminderType(Reminder.WHEN_I_LEAVE);
	            		
	            		rem.setCreated(new Date());
	            		rem.setPriority(1);
	            		rem.setisReminded(0);
	            		
	            		
	            		placeitDB adb = new placeitDB(AddNewReminder.this);
	            		adb.open();
	            		long returnData = adb.createEntry(rem);
	            		Log.d("Testing", " " +returnData);
	            		adb.close();
	            	  // Toast.makeText(getApplicationContext(), "Longitude: " + longitude + " Latitute: " + latitude + "Radius" + radius ,5).show();
	            		
	            	  // Toast.makeText(getApplicationContext(), "Longitude: " + longitude + " Latitute: " + latitude + " " + reminderText.getText().toString(),5).show();
	            		
	            		Toast.makeText(getApplicationContext(), "Reminder Saved!", 5).show();
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   
	               }
	           });
			
			alert.show();
			
		}
			
	
	}
	@Override
	public void onLocationChanged(Location location) {
		 longitude = location.getLongitude();
	     latitude = location.getLatitude();
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}*/
