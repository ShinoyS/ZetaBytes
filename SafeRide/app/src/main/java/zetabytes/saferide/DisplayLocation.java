package zetabytes.saferide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 23-08-2015.
 */
public class DisplayLocation {
    String displayLocation()
    {            String data="Locations:\n";
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
               data+=place+"\n";
            }
        } catch (Exception e) {e.printStackTrace();}
        return data;
    }

}
