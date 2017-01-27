package com.rawat.anand.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand Rawat on 27-01-2017.
 */

public final class DBUtility {

    public static List<BusStop> stops = null;
    public static boolean isNotUpdated = true;

    public static void loadDBStops(Context con) {
        if (isNotUpdated || stops == null) {
            DBHandler dbHandler = new DBHandler(con, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
            stops = dbHandler.getAllBusStops();
            isNotUpdated = false;
        }
        if (stops == null)
            stops = new ArrayList<BusStop>();
    }
}
