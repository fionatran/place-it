package com.example.placeitmap;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	/*	Toast.makeText(getApplicationContext(), " Starting Service",Toast.LENGTH_LONG).show();
		
		if (!isMyServiceRunning()) 
		{
			Intent myServiceIntent = new Intent(MainActivity.this, MyService.class);
			PendingIntent pimyService = PendingIntent.getService(MainActivity.this, 0,
					myServiceIntent,0);
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			//alarmManager.cancel(pimyService);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), 1000 * 10, pimyService);
		}
		
		startService(new Intent(this, MyService.class));
		
		Toast.makeText(getApplicationContext(), " Finish Creating Service",Toast.LENGTH_LONG).show();
		*/

		TabHost tabHost = getTabHost();
		TabSpec spec1 = tabHost.newTabSpec("Tab 1");
		TabSpec spec2 = tabHost.newTabSpec("Tab 2");
		//tabHost.setup();

		
		Intent tabIntent1 = new Intent(MainActivity.this, AddNewReminder.class );
		spec1.setContent(tabIntent1);
		//spec1.setContent(R.id.tab1);
		spec1.setIndicator("Add new reminder");

		
		Intent tabIntent2 = new Intent(MainActivity.this, ExistGoogleMaps.class );
		spec2.setContent(tabIntent2);
		spec2.setIndicator("Existing reminders");
		//spec2.setContent(R.id.tab2);


		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
