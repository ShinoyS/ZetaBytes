package zetabytes.saferide;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ASUS on 23-08-2015.
 */
public class DiffDates {
    long diffDates(Date d1,Date d2)
    {
        Timestamp ts1=new Timestamp(d1.getTime());
        Timestamp ts2=new Timestamp(d2.getTime());
        return ts1.getTime()-ts2.getTime();
    }

}
