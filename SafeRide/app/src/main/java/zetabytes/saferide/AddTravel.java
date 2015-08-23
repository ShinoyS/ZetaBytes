package zetabytes.saferide;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 23-08-2015.
 */
public class AddTravel {
    void addTravel(String s, String d, String time)
    {
        try{
            SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("saferide", null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS travels(loc1 VARCHAR,loc2 VARCHAR, time VARCHAR);");
            mydatabase.execSQL("INSERT INTO travels VALUES('"+s+"','"+d+"','"+time+"');");

        }
        catch(Exception e){e.printStackTrace();}
    }

}
