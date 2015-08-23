package zetabytes.saferide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 23-08-2015.
 */
public class RemoveLocation {
    void removeLocation(String n)
    {
        try{
            SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("saferide", null);
            mydatabase.execSQL("delete from locations where name='"+n+"'");
            
        }
        catch(Exception e){e.printStackTrace();}
    }

}
