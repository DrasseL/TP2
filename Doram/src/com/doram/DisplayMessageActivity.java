package com.doram;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DisplayMessageActivity extends ActionBarActivity{
	
	MySQLiteHelper db = new MySQLiteHelper(this);
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_display_message);
		Intent intent = getIntent();
		
		//String textIntent = getIntent().getExtras().getString("extraUsername");
		
		
		TextView txtUsername = (TextView)this.findViewById(R.id.txtusername);
		
		//String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		String theUsername = intent.getStringExtra("extraUsername");
		//String thePassword = intent.getStringExtra("extraPassword");
		
		//TextView txtUsername = new TextView(this);
		txtUsername.setText(theUsername);
		//setContentView(txtUsername);
		//Toast.makeText(this, theUsername, Toast.LENGTH_SHORT).show();
		
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}
	
	public void addFaction(View view){
		
		db.addFaction(new Faction("Nation", "La Nation est en bleu"));
		db.addFaction(new Faction("Rebellion", "La Rebellion est en orange"));
	}
	
	public void getFaction(View view){
		
		//Toast.makeText(this, theUsername, Toast.LENGTH_SHORT).show();
		Toast.makeText(this, db.getFaction(2).toString(), Toast.LENGTH_LONG).show();
	}
	
	public void getAllFactions(View view){
		
		Toast.makeText(this, db.getAllFactions().toString(), Toast.LENGTH_LONG).show();
	}
	
	public void deleteFactions(View view){
		db.deleteFactions();
	}
}
