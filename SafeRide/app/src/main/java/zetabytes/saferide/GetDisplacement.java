package zetabytes.saferide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ASUS on 23-08-2015.
 */
public class GetDisplacement {
    int displacement(String src,String dest)
    {
        int srcflag=0,dstflag=0;
        String slat="",slon="",dlat="",dlon="";
        try {
            SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("saferide", null);
            Cursor resultSet = mydatabase.rawQuery("Select * from locations", null);
            resultSet.moveToFirst();
            int count=resultSet.getCount();
            for(int i=0;i<count;i++) {
                String place = resultSet.getString(1);
                String lat = resultSet.getString(2);
                String lon = resultSet.getString(3);
                resultSet.moveToNext();
                if(place.equals(src))
                {
                    slat=lat;
                    slon=lon;
                    srcflag=1;
                }
                if(place.equals(dest))
                {
                    dlat=lat;
                    dlon=lon;
                    dstflag=1;
                }
               // System.out.println(place + "," + lat + "," + lon);
            }
            if(srcflag==0||dstflag==0)
                return -1;
            String s=slat+","+slon;
            String d=dlat+","+dlon;
            String a="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+s+"&destinations="+d;
            // 			  	System.out.println(a);
            URL url;

            try {
                // get URL content

                //   String a="http://localhost:8080/TestWeb/index.jsp";
                url = new URL(a);
                URLConnection conn = url.openConnection();

                // open the stream and put it into BufferedReader
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                return getDist(br);
            } catch (Exception e) {e.printStackTrace();}
            //return getDist();
        }
        catch(Exception e){}
        return 1;
    }
    int getDist(BufferedReader br) throws Exception
    {
        String inputLine,data="";
        int state=0;
        while ((inputLine = br.readLine()) != null) {
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
        br.close();
        return Integer.parseInt(numberOnly);
    }

}
