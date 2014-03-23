package com.example.proxshop;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceholderFragment extends Fragment implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, OnClickListener {
	Button load;
	TextView position_gps;
	LocationManager locationManager;
	LocationListener locationListener;
	Location location;
	String s;
	double latitude,longitude;
	Boolean isGPSEnabled,isNetworkEnabled;
	private final static int MIN_TIME_BW_UPDATES= 3000, MIN_DISTANCE_CHANGE_FOR_UPDATES = 4;
	
	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		position_gps= (TextView) rootView.findViewById(R.id.position);
		load = (Button) rootView.findViewById(R.id.load_map_button);
		load.setOnClickListener(this);
		
		locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		
		String message;
		if(PositionHandler.imInTheSquare(getLocation().getLatitude(), getLocation().getLongitude())){
			message="Ti trovi sulla superficie del tuo palazzo";
		}else{
			message="Ti trovi fuori zona !!";	
			 /* TODO: Implementare le google maps api per usare marker etc. 
			  * 		Serve una chiave
			  * */

		}
		Toast toast = Toast.makeText(getActivity().getApplicationContext(),
										message	, Toast.LENGTH_LONG);
		toast.show();
		return rootView;
	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				Uri.parse("geo:0,0?q="+getLocation().getLatitude()+","+getLocation().getLongitude()+ "( CiaoStronzolo )"));
				startActivity(intent);
		
	}

	@Override
  public void onLocationChanged(Location location) {
      
		String message;
		if(PositionHandler.imInTheSquare(location.getLatitude(), location.getLongitude())){
			message="Ti trovi sulla superficie del tuo palazzo";
		}else{
			message="Ti trovi fuori zona !!";	
		}
      Toast.makeText(getActivity().getBaseContext(), message, Toast.LENGTH_LONG).show();
  }

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
      /* Called when User on Gps   */
      
      Toast.makeText(getActivity().getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
      /* Called when User off Gps */
      
      Toast.makeText(getActivity().getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume(){
		getLocation();
	}
	
	@Override
	public void onPause() {
		locationManager.removeUpdates(this);
		super.onPause();
	}
	
	public Location getLocation() {
	    try {
	    	
	        locationManager = (LocationManager) getActivity()
	                .getSystemService(Context.LOCATION_SERVICE);

	        // getting GPS status
	        isGPSEnabled = locationManager
	                .isProviderEnabled(LocationManager.GPS_PROVIDER);

	        // getting network status
	        isNetworkEnabled = locationManager
	                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	        if (!isGPSEnabled && !isNetworkEnabled) {
	            // no network provider is enabled
	        } else {
	            //this.canGetLocation = true;
	            if (isNetworkEnabled) {
	                locationManager.requestLocationUpdates(
	                        LocationManager.NETWORK_PROVIDER,
	                        MIN_TIME_BW_UPDATES,
	                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                Log.d("Network", "Network Enabled");
	                if (locationManager != null) {
	                    location = locationManager
	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                    if (location != null) {
	                        latitude = location.getLatitude();
	                        longitude = location.getLongitude();
	                    }
	                }
	            }
	            // if GPS Enabled get lat/long using GPS Services
	            if (isGPSEnabled) {
	                if (location == null) {
	                    locationManager.requestLocationUpdates(
	                            LocationManager.GPS_PROVIDER,
	                            MIN_TIME_BW_UPDATES,
	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                    Log.d("GPS", "GPS Enabled");
	                    if (locationManager != null) {
	                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                        if (location != null) {
	                            latitude = location.getLatitude();
	                            longitude = location.getLongitude();
	                        }
	                    }
	                }
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return location;
	}
	
	
	
}
