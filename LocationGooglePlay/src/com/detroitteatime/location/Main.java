package com.detroitteatime.location;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.detroitteatime.locationgoogleplay.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

public class Main extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	private LocationClient mLocationClient;
	private TextView latView;
	private TextView longView;
	private TextView info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mLocationClient = new LocationClient(this, this, this);
		
		latView = (TextView)findViewById(R.id.latitudeView);
		longView = (TextView)findViewById(R.id.longitudeView);
		info =  (TextView)findViewById(R.id.info);

	}

	@Override
	protected void onStart() {
		super.onStart();
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			Toast.makeText(this, "Install Google Play Services.",
					Toast.LENGTH_SHORT).show();
			finish();
		}else{
		
			mLocationClient.connect();	
		}
	}


	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	

	// Methods to implement GooglePlaySercicesClient.ConnectionCallbacks
	@Override
	public void onConnected(Bundle connectionHint) {
		Location mCurrentLocation = mLocationClient.getLastLocation();
		String latText = "Latitude: " + String.valueOf(mCurrentLocation.getLatitude());
		String longText = "Longitude: " + String.valueOf(mCurrentLocation.getLongitude());
		String provider = "provider: " +String.valueOf(mCurrentLocation.getProvider());
		String accuracy = " accuracy: " + String.valueOf(mCurrentLocation.getAccuracy());

		
		latView.setText(latText);
		longView.setText(longText);
		info.setText("Other info: " + provider + accuracy );
	}

	@Override
	public void onDisconnected() {

	}

	// Methods to implement GooglePlaySercicesClient.OnConnectionFailedListener
	@Override
	public void onConnectionFailed(ConnectionResult result) {

	}

	

}
