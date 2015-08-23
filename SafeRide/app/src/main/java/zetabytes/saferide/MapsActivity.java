package zetabytes.saferide;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    double lat=0,lon=0;
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mydb=new DBHelper(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        //LatLng sydney = new LatLng(-34, 151);
        //map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng position) {
                System.out.println(position.latitude + "," + position.longitude);
                LatLng myloc=new LatLng(position.latitude,position.longitude);
                map.addMarker(new MarkerOptions().position(myloc));
                lat=position.latitude;
                lon=position.longitude;
            }
        });
        Button b3 = (Button) findViewById(R.id.setRangeButton);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                EditText text = (EditText) findViewById(R.id.editText);
                String value = text.getText().toString();
                //new AddLocation().addLocation(value,lat+"",lon+"");
                mydb.insertLocation(value, lat + "", lon + "");
                finish();
            }
        });
        Button b10 = (Button) findViewById(R.id.button10);
        b10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                finish();
            }
        });
    }
}