package zetabytes.saferide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class TripActivity extends ActionBarActivity {
    DBHelper mydb;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        mydb=new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        String data="Locations:\n";
        if(extras !=null)
        {
            ArrayList<String> values=getIntent().getStringArrayListExtra("locations");
            for(int i=0;i<values.size();i++)
            {
                data+=values.get(i)+"\n";
            }
        }
        t=(TextView)findViewById(R.id.textView4);
        t.setText(data);
        Button b5 = (Button) findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                EditText text = (EditText) findViewById(R.id.editText4);
                String from = text.getText().toString();
                text = (EditText) findViewById(R.id.editText5);
                String to = text.getText().toString();
                text = (EditText) findViewById(R.id.editText6);
                String time = text.getText().toString();
                //new AddLocation().addLocation(value,lat+"",lon+"");
                mydb.insertTravel(from, to, time);
                finish();
            }
        });
        Button b9 = (Button) findViewById(R.id.button9);
        b9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip, menu);
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
