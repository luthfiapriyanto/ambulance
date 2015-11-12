package com.example.luthf.ambulance;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

    // the Google Map object
    private GoogleMap mMap;

    // LatLng objects store a pair of terrestrial coordinates (latitude and longitude)
    // STARTING_MARKER_POSITION first values are the coordinates of the Colosseo in Rome (Italy)
    private static LatLng STARTING_MARKER_POSITION =new LatLng(-6.193101, 106.804470);

    /* distanceFrom indicates the starting point to calculate the distance from.
       It's initialized with STARTING_MARKER_POSITION
    */

    private LatLng distanceFrom= STARTING_MARKER_POSITION;

    // A Geocoder can transform a pair of latitude/longitude into a street address and viceversa.
    // We'll use it in the listener
    private static Geocoder geocoder=null;


    private GoogleMap.OnMapClickListener clickListener=new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(final LatLng pos) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // we set the layout for the Activity
        setContentView(R.layout.activity_main);

        // the geocoder is instantiated for the first time
        geocoder = new Geocoder(this);

        // if there isn't a map, it will be created
        setUpMapIfNeeded();

        mMap.setMyLocationEnabled(true);



    @Override
    findViewById(R.id.email_sign_in_button1).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, ETA.class));
        }
    });

}
    protected void onResume() {
        super.onResume();

        // the availability of the GoogleMap will be checked before the Activity starts interacting with the user
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        // the map is created only it has not been initialized
        if (mMap == null) {

            // the map is located in the layout
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            // if a map exists, we proceed with initialization
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    // Now it's time to configure the map. We can add markers, shapes, event handlers and so on
    private void setUpMap() {

        // the camera will be positioned according to the new coordinates
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(STARTING_MARKER_POSITION, 16));

        // we choose the type of the map: Satellite in this case
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // markerOptions describes the marker we want to place
        MarkerOptions markerOptions=new MarkerOptions()
                .position(STARTING_MARKER_POSITION)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark))
                .draggable(true);
        // the marker has to be draggable as we'll move it

        // the marker is rendered on the map
        mMap.addMarker(markerOptions);

        // we define the object to invoke when the marker is dragged
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDragStart(Marker arg0)
            {
                // this method is called when the drag starts
                // the operation we need is the cancellation of a preexisting line
            }
            @Override
            public void onMarkerDragEnd(final Marker pos)
            {
                // we get the final position of the marker
                distanceFrom=pos.getPosition();

            }

            @Override
            public void onMarkerDrag(Marker arg0)
            {
                // operations performed during the movement. Nothing to do
            }
        });

        // the callback to invoke is set
        mMap.setOnMapClickListener(clickListener);
    }

}
