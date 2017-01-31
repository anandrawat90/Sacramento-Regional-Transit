package com.rawat.anand.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand Rawat on 27-01-2017.
 */

public final class DBUtility {

    private final static Object mlock = new Object();
    private static DBUtility instance = null;
    private List<BusStop> stops = null;
    private boolean isNotUpdated = true;

    private DBUtility() {
    }

    public static DBUtility getInstance() {
        if (instance == null)
            synchronized (mlock) {
                instance = new DBUtility();
            }
        return instance;
    }

    public void loadDBStops(Context con) {
        if (isNotUpdated || stops == null) {
            DBHandler dbHandler = new DBHandler(con, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
            stops = dbHandler.getAllBusStops();
            isNotUpdated = false;
        }
        if (stops == null)
            stops = new ArrayList<BusStop>();
    }

    public boolean isNotUpdated() {
        return this.isNotUpdated;
    }

    public void setIsNotUpdated(boolean isNotUpdated) {
        synchronized (mlock) {
            this.isNotUpdated = isNotUpdated;
        }
    }

    public List<BusStop> getStops() {
        return this.stops;
    }

}
