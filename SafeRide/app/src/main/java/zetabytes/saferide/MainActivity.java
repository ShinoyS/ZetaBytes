package zetabytes.saferide;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);

        Button b3 = (Button) findViewById(R.id.button);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                Intent i = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(i);
            }
        });
        Button b4 = (Button) findViewById(R.id.button2);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                ArrayList<String> array_list = mydb.getAllLocations();
                Bundle dataBundle = new Bundle();
                dataBundle.putStringArrayList("locations",array_list);
                Intent i = new Intent(getBaseContext(), LocationChoice.class);
                i.putExtras(dataBundle);
                startActivity(i);
            }
        });
        Button b5 = (Button) findViewById(R.id.button6);
        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                ArrayList<String> array_list = mydb.getAllLocations();
                Bundle dataBundle = new Bundle();
                dataBundle.putStringArrayList("locations",array_list);
                Intent i = new Intent(getBaseContext(), TripActivity.class);
                i.putExtras(dataBundle);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
