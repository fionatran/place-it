package com.example.placeitmap;

import java.util.ArrayList;
import com.example.placeitmap.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.example.placeitmap.database.placeitDB;
import com.example.placeitmap.dto.Reminder;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExistGoogleMaps extends FragmentActivity implements
		LocationListener {
	double latitude;
	double longitude;
	GoogleMap googleMap;
	UiSettings uiSettings;
	// Marker selectedLocation;
	Button SelectOkButton;
	private LocationManager locationManager;
	ArrayList<Marker> markers;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exist_google_maps);
		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		}
		setUpMap();
	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to enable GPS?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		for (Marker m : markers) {
			m.remove();
		}

		setMarkersOnMap();
	}

	/*
	 * @Override protected void onPause() { // TODO Auto-generated method stub
	 * //super.onPause();
	 * 
	 * }
	 */

	private void setGestures() {
		googleMap.setMyLocationEnabled(true);
		uiSettings.setAllGesturesEnabled(true);
	}

	private void setMarkersOnMap() {
		markers = new ArrayList<Marker>();
		ArrayList<Reminder> reminders;
		placeitDB adb = new placeitDB(this);
		adb.open();
		reminders = adb.getData();
		adb.close();
		if (reminders == null)
			return;
		for (Reminder reminder : reminders) {
			if (reminder.getisReminded() == 1) {
				Toast.makeText(this, reminder.getRemiderText()+" TYpe : "+reminder.getisReminded(),Toast.LENGTH_SHORT).show();
				markers.add(googleMap.addMarker(new MarkerOptions()
						.position(
								new LatLng(reminder.getLatitude(), reminder
										.getLongitude()))
						.snippet(reminder.getCreated().toLocaleString())
						.draggable(false)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.title(reminder.getRemiderText())));
			} else {
				if (reminder.getReminderType() == reminder.WHEN_I_ARRIVE) {
					markers.add(googleMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(reminder.getLatitude(), reminder
											.getLongitude()))
							.snippet(reminder.getCreated().toLocaleString())
							.draggable(false)
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
							.title(reminder.getRemiderText())));
				} else if (reminder.getReminderType() == reminder.WHEN_I_LEAVE) {
					markers.add(googleMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(reminder.getLatitude(), reminder
											.getLongitude()))
							.snippet(reminder.getCreated().toLocaleString())
							.draggable(false)
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
							.title(reminder.getRemiderText())));
				}
			}
		}

	}

	private void setUpMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
		if (googleMap != null) {
			uiSettings = googleMap.getUiSettings();
			// googleMap.setOnMapClickListener(this);
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE,
					this);
			setGestures();
			setMarkersOnMap();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * @Override public void onMapClick(LatLng latLng) { latitude =
	 * latLng.latitude; longitude = latLng.longitude;
	 * 
	 * Toast.makeText(this.getApplicationContext(), "Latitude: " + latitude +
	 * " Longitude: " + longitude, Toast.LENGTH_SHORT).show(); if
	 * (selectedLocation != null) selectedLocation.remove(); selectedLocation =
	 * googleMap.addMarker(new MarkerOptions() .position(latLng)
	 * .draggable(true) .icon(BitmapDescriptorFactory
	 * .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)) .title("here")); }
	 */
	@Override
	public void onLocationChanged(Location location) {
		if (location == null)
			return;
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
				10);
		googleMap.animateCamera(cameraUpdate);
		locationManager.removeUpdates(this);
		googleMap.getMaxZoomLevel();
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}