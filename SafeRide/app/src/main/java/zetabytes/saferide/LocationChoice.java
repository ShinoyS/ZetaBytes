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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class LocationChoice extends ActionBarActivity {
    DBHelper mydb;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_choice);
//        String loclist=new DisplayLocation().displayLocation();
        mydb=new DBHelper(this);
        t=new TextView(this);
        Bundle extras = getIntent().getExtras();
        String data="Locations:\n";
        if(extras !=null)
        {
            ArrayList<String> values=getIntent().getStringArrayListExtra("locations");
            for(int i=0;i<values.size();i++)
            {
                data+=values.get(i)+",";
            }
        }
        t=(TextView)findViewById(R.id.textView);
        t.setText(data);
        Button b4 = (Button) findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                EditText text = (EditText) findViewById(R.id.editText2);
                String from = text.getText().toString();
                text = (EditText) findViewById(R.id.editText3);
                String to = text.getText().toString();
                //String qury = mydb.getDistance(from, to);
                int dist = mydb.getDistance(from, to);
                if (dist == -1) {
                    t = (TextView) findViewById(R.id.textView);
                    t.setText("Source or destination not found in list!");
                } else {
                    t = (TextView) findViewById(R.id.textView);
                    t.setText("Distance is " + dist + " metres");
                }
                //t = (TextView) findViewById(R.id.textView);
                //t.setText("Distance is "+qury+" metres");
            }
        });
        Button b7 = (Button) findViewById(R.id.button7);
        b7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                EditText text = (EditText) findViewById(R.id.editText2);
                String from = text.getText().toString();
                text = (EditText) findViewById(R.id.editText3);
                String to = text.getText().toString();
                //String qury = mydb.getDistance(from, to);
                int tme=mydb.estimate(from, to);
                if(tme==-1)
                {
                    t=(TextView)findViewById(R.id.textView);
                    t.setText("Source or destination not found in list!");
                }
                else
                {
                    int dist = mydb.getDistance(from, to);
                    float speed=(((float)dist/(float)tme)/1000)*60;
                    t=(TextView)findViewById(R.id.textView);
                    t.setText("Estimated time is "+(tme/60)+" hours and "+(tme%60)+" minutes at "+speed+"kph");
                }
                //t = (TextView) findViewById(R.id.textView);
                //t.setText("Distance is "+qury+" metres");
            }
        });
        Button b5 = (Button) findViewById(R.id.button3);
        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                finish();
            }
        });
        Button b8 = (Button) findViewById(R.id.button8);
        b8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Intent i = new
                //       Intent(android.content.Intent.ACTION_VIEW,
                //      Uri.parse("geo:37.827500,-122.481670"));
                //startActivity(i);
                EditText text = (EditText) findViewById(R.id.editText7);
                String dy = text.getText().toString();
                text = (EditText) findViewById(R.id.editText8);
                String tm = text.getText().toString();
                Date curr=new Date();
                DateFormat format = new SimpleDateFormat("dd/MM/yy,hh:mm", Locale.ENGLISH);
                Date tgt = curr;
                try {
                    tgt = format.parse(dy+","+tm);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //	System.out.println(curr.toString()+","+tgt.toString());
                long diff=new DiffDates().diffDates(tgt,curr);
                diff/=1000;
                text = (EditText) findViewById(R.id.editText2);
                String from = text.getText().toString();
                text = (EditText) findViewById(R.id.editText3);
                String to = text.getText().toString();
                //String qury = mydb.getDistance(from, to);
                int dist = mydb.getDistance(from, to);
                if(dist==-1)
                    System.out.println("Source or destination not found in list!");
                else
                {
                    float speed=(float)dist/(float)diff;
                    speed*=3.6;
                    String str=diff+ "seconds to go";
                    str+=". You have to move at "+speed+"kph";
                    t=(TextView)findViewById(R.id.textView);
                    t.setText(str);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_choice, menu);
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
