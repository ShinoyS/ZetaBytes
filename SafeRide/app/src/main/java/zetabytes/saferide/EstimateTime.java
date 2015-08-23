package zetabytes.saferide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 23-08-2015.
 */
public class EstimateTime {
    int estimate(String s, String d)
    {
        try {
            SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("saferide", null);
            Cursor resultSet = mydatabase.rawQuery("Select * from travels", null);
            int dist=new GetDisplacement().displacement(s,d);
            if(dist==-1)
                return -1;
            float temp1=30000/(float)dist;
            float temp2=60/temp1;
            int count=0,total=(int)temp2+1,estimate;
            resultSet.moveToFirst();
            int countrow=resultSet.getCount();
            for(int i=0;i<countrow;i++) {
                String src = resultSet.getString(1);
                String dst = resultSet.getString(2);
                String tym = resultSet.getString(3);
                resultSet.moveToNext();
                if((src.equals(s)&&dst.equals(d))||(src.equals(d)&&dst.equals(s)))
                {
                    total+=Integer.parseInt(tym);
                    //System.out.println("Found an instance with time "+Integer.parseInt(parts[2])+" minutes");
                    count++;
                }
            }

            //System.out.println("Total: "+total+" Instances: "+(count+1));
            estimate=total/(count+1);
            //System.out.println("Estimated time: "+estimate);
            return estimate;
            //return (dist/estimate)+1;
        } catch (Exception e) {}
        return 1;
    }

}
