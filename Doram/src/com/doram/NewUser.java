package com.doram;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.os.Build;

public class NewUser extends ActionBarActivity {

	MySQLiteHelper db = new MySQLiteHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		Intent intent = getIntent();
		
		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_new_user,
					container, false);
			return rootView;
		}
	}

	public void saveUser(View view){
		EditText theUsername = (EditText)findViewById(R.id.edtusername);
		EditText thePassword = (EditText)findViewById(R.id.edtpassword);
		
		db.addFaction(new Faction("Nation", "La Nation est en bleu"));
		
	}
	
	public void createRank(View view){
		db.addRanks(new Rank("Officer"));
	}
	
	public void viewRank(View view){
		Toast.makeText(this, db.getAllRanks().toString(), Toast.LENGTH_LONG).show();
	}
	
	public void radioClick(View view){
		boolean checked = ((RadioButton)view).isChecked();
		
		switch(view.getId()){
		case R.id.radioNation:
			if(checked)
				break;
		case R.id.radioRebellion:
			if(checked)
				break;
		}
		
		
		
	}
}
