package com.example.gmapsapp;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;


import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	// can be any value
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	GoogleMap mMap;
	private static final double SEATTLE_LAT = 47.60621,
			SEATTLE_LNG =-122.33207, 
			SYDNEY_LAT = -33.867487,
			SYDNEY_LNG = 151.20699, 
			NEWYORK_LAT = 40.714353, 
			NEWYORK_LNG = -74.005973;
			private static final float DEFAULTZOOM = 15;
			private static final String LOGTAG = "Maps";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// display map if access to services is enabled else dont
		if (servicesOK())
		{
			
		
			setContentView(R.layout.activity_map);
			
			
			if(initMap())
			{
				Toast.makeText(this,"Ready to Map!",Toast.LENGTH_SHORT).show();
				gotoLocation(SYDNEY_LAT,SYDNEY_LNG,DEFAULTZOOM);
			}
			else
			{
				Toast.makeText(this,"Map not available!",Toast.LENGTH_SHORT).show();
			}
			
		}
		
		else
		{
			setContentView(R.layout.activity_main);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public boolean servicesOK()
	{
		int isAvailable=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		//check if its availabl on device
		if(isAvailable==ConnectionResult.SUCCESS)
		{
			return true;
		}
		
		else if(GooglePlayServicesUtil.isUserRecoverableError(isAvailable))
		{
			Dialog dialog=GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}
		// error not recoverable by user
		else
		{
			Toast.makeText(this,"Cant connect to Play services",Toast.LENGTH_SHORT).show();
			
		}
		
		 return false;
	}
	
	
	private boolean initMap()
	{
		if(mMap==null)
		{
			SupportMapFragment mapFrag=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		
		mMap=mapFrag.getMap();
		}
		
		return(mMap!=null);
		
	}
	
	private void gotoLocation(double lat,double lng, float zoom)
	{
		LatLng ll= new LatLng(lat, lng);
		CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,zoom);
		mMap.moveCamera(update);
	}
	
 public boolean onOptionsItemSelected(MenuItem item)
 {
	 switch(item.getItemId())
	 {
	 
	 case R.id.mapTypeNone:
		 mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
		 break;
	 case R.id.mapTypeNormal:
		 mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 break;
	 case R.id.mapTypeSatellite:
		 mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		 break;
	 case R.id.mapTypeHybrid:
		 mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		 break;
	 case R.id.mapTypeTerrain:
		 mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		 break;
	 }
	 
	 return super.onOptionsItemSelected(item);
 }
	
 @Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	MapStateManager mgr=new MapStateManager(this);
	mgr.saveMapState(mMap);
	
	
	
}
 
 
 
	 
}
