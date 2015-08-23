package zetabytes.saferide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 23-08-2015.
 */
public class AddLocation {
    void addLocation(String n, String lt, String ln)
    {
       try{
            SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("saferide", null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS locations(name VARCHAR,latitude VARCHAR, longitude VARCHAR);");
            mydatabase.execSQL("INSERT INTO locations VALUES('"+n+"','"+lt+"','"+ln+"');");
            System.out.println("Done");
        }
        catch(Exception e){e.printStackTrace();}
    }

}
