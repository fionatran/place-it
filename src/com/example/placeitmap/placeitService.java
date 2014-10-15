package com.example.placeitmap;

import java.util.ArrayList;

import com.example.placeitmap.database.placeitDB;
import com.example.placeitmap.dto.Reminder;
import com.example.placeitmap.services.MyLocation;
import com.example.placeitmap.services.MyLocation.LocationResult;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class placeitService extends Service {

	double Latitude, Longitude;
	Location currtentLocation;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service Started !", Toast.LENGTH_SHORT).show();
		popNotification("placeit", "Starting", null);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		LocationResult locationResult = new LocationResult() {
			@Override
			public void gotLocation(Location location) {
				// currtentLocation = location;
				Latitude = location.getLatitude();
				Longitude = location.getLongitude();
			}
		};
		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(placeitService.this, locationResult);

		currtentLocation = new Location("");
		currtentLocation.setLatitude(Latitude);
		currtentLocation.setLongitude(Longitude);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this, " Lat : " + Latitude + "Long : " + Longitude,
				Toast.LENGTH_LONG).show();

		if (Latitude != 0.0)
			checkDistance();

		return START_STICKY;
	}

	@SuppressWarnings("deprecation")
	private void checkDistance() {
		placeitDB adb = new placeitDB(this);
		adb.open();
		ArrayList<Reminder> rmd;
		rmd = adb.getData();
		Location ln = new Location("");
		float dist;
		for (Reminder reminder : rmd) {
			if (reminder.getisReminded() == 0) {
				ln.setLatitude(reminder.getLatitude());
				ln.setLongitude(reminder.getLongitude());
				dist = currtentLocation.distanceTo(ln);
				Toast.makeText(
						this,
						"Dist : " + dist + " TYpe : "
								+ reminder.getReminderType(),
						Toast.LENGTH_SHORT).show();

				if ((reminder.getReminderType() == Reminder.WHEN_I_ARRIVE)
						&& (dist <= reminder.getRadius())) {

					popNotification("placeit", reminder.getRemiderText(),
							reminder.getCreated().toLocaleString());
					reminder.setisReminded(1);
					adb.updateEntry(reminder);

				} else if ((reminder.getReminderType() == Reminder.WHEN_I_LEAVE)
						&& (dist > (reminder.getRadius()))) {

					popNotification("placeit", reminder.getRemiderText(),
							reminder.getCreated().toLocaleString());
					reminder.setisReminded(1);
					adb.updateEntry(reminder);
				}
			}
		}
		adb.close();
	}

	@SuppressWarnings("deprecation")
	private void popNotification(String text, CharSequence title,
			CharSequence detail) {
		// TODO Auto-generated method stub
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		@SuppressWarnings({})
		
		Intent intent2 = new Intent(this, MainActivity.class);
		intent2.setAction(Intent.ACTION_MAIN);
	    intent2.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent2, 0);
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.placeit_launcher)
			    .setContentTitle(title)
			    .setContentText(text);
		mBuilder.setContentIntent(pendingIntent); 

		notificationManager.notify(0, mBuilder.build());
		try {
			Uri noti = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
					noti);
			r.play();
		} catch (Exception e) {
		}
	}
}
