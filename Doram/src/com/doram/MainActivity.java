package com.doram;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements LocationListener{
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	TextView txtLat;
	TextView txtUsername;
	String lat;
	String provider;
	protected String latitude,longitude; 
	protected boolean gps_enabled,network_enabled;
	//public final static String EXTRA_MESSAGE = "com.doram.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtLat = (TextView) findViewById(R.id.txtlocation);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		
		// DO NOT REMOVE THIS IF CODE BECAUSE DORAM WILL EXPLODE
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	// Called when the user clicks the Send button
	public void sendMessage(View view){
		EditText TxtUsername = (EditText) findViewById(R.id.edtusername);
		EditText TxtPassword = (EditText) findViewById(R.id.edtpassword);
		
		String theUsername = TxtUsername.getText().toString();
		String thePassword = TxtPassword.getText().toString();
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		
		// putExtra adds additional data to the intent
		//intent.putExtra(EXTRA_MESSAGE, theUsername);
		intent.putExtra("extraUsername", theUsername);
		intent.putExtra("extraPassword", thePassword);
		startActivity(intent);
		
	}
	
	public void createUser(View view){
		Intent intent = new Intent(this, NewUser.class);
		startActivity(intent);
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();  // Always call the superclass
	    
	    // Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}

	@Override
	public void onLocationChanged(Location location) {
		Double myLatitude = location.getLatitude();
		Double myLongitude = location.getLongitude();
		String CityName;
		txtLat = (TextView) findViewById(R.id.txtlocation);
		//txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
		
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocation(myLatitude, myLongitude, 1);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		 CityName = addresses.get(0).getLocality();
		 txtLat.setText(CityName);
		//Toast.makeText(this, CityName, Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public void onProviderDisabled(String provider) {
		Log.d("Latitude","disable");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("Latitude","enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("Latitude","status");
	}
}



