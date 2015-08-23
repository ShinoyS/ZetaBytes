package zetabytes.saferide;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by ASUS on 23-08-2015.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "saferide.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS locations(name VARCHAR,latitude VARCHAR, longitude VARCHAR);"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS travels(loc1 VARCHAR,loc2 VARCHAR, time VARCHAR);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertLocation  (String n, String lt, String ln)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", n);
        contentValues.put("latitude", lt);
        contentValues.put("longitude", ln);
        db.insert("locations", null, contentValues);
        return true;
    }

    public boolean insertTravel  (String s, String d, String t)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("loc1", s);
        contentValues.put("loc2", d);
        contentValues.put("time", t);
        db.insert("travels", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("loc1", d);
        contentValues.put("loc2", s);
        contentValues.put("time", t);
        db.insert("travels", null, contentValues);
        return true;
    }

    public Cursor getLocation(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from locations where name='"+id+"'", null );
        return res;
    }

    public int getDistance(final String src, final String dest) {
        final int[] respdist = {0};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from locations where name='" + src + "'", null);
        res.moveToFirst();
        final String s = res.getString(res.getColumnIndex("latitude")) + "," + res.getString(res.getColumnIndex("longitude"));
        res.close();
        res = db.rawQuery("select * from locations where name='" + dest + "'", null);
        res.moveToFirst();
        final String d = res.getString(res.getColumnIndex("latitude")) + "," + res.getString(res.getColumnIndex("longitude"));
        res.close();
        final int[] sem = {0};
        new Thread(new Runnable(){
            @Override
            public void run() {
                sem[0] =1;
                try {
                    //Your implementation goes here


        String a = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + s + "&destinations=" + d;
        System.out.println(a);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(a));
            StatusLine statusLine = response.getStatusLine();
            System.out.println(statusLine);
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                String responseString = out.toString();
                respdist[0] = getDist(responseString);
                System.out.println("response: "+responseString);
                System.out.println("Distance: " + respdist[0]);
                out.close();

              //  return respdist;
            }
        } catch (Exception e) { e.printStackTrace();
        }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }             sem[0]=0;
            }
        }).start();
        while(respdist[0]==0){}
        return respdist[0];
    }

    int estimate(String s, String d)
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor resultSet = db.rawQuery("select * from travels", null);
            int dist=getDistance(s,d);
            if(dist==-1)
                return -1;
            float temp1=30000/(float)dist;
            float temp2=60/temp1;
            int count=0,total=(int)temp2+1,estimate;
            resultSet.moveToFirst();
            int countrow=resultSet.getCount();
            for(int i=0;i<countrow;i++) {
                String src = resultSet.getString(0);
                String dst = resultSet.getString(1);
                String tym = resultSet.getString(2);
                resultSet.moveToNext();
                if((src.equals(s)&&dst.equals(d)))
                {
                    total+=Integer.parseInt(tym);
                    System.out.println("Found an instance with time "+tym+" minutes");
                    count++;
                }
            }

            System.out.println("Total: "+total+" Instances: "+(count+1));
            estimate=total/(count+1);
            System.out.println("Estimated time: "+estimate);
            return estimate;
            //return (dist/estimate)+1;
        } catch (Exception e) {e.printStackTrace();}
        return 1;
    }

    int getDist(String s) throws Exception
    {
        String[] parts=s.split("\n");
        String inputLine,data="";
        int state=0;
        for(int i=0;i<parts.length;i++){
            inputLine=parts[i];
            if(inputLine.contains("ZERO_RESULT"))
                return -1;
            if(state==0&&inputLine.contains("distance"))
                state=1;
            if(state==1&&inputLine.contains("value"))
            {
                state=0;
                data=inputLine;
            }
        }
        String numberOnly= data.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }
    public Cursor getDuration(String src,String dest){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from travels where loc1='"+src+"' and loc2='"+dest+"'", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public Integer deleteLocation (String n)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("locations",
                "name= ? ",
                new String[] { n });
    }

    public ArrayList<String> getAllLocations()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from locations", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
