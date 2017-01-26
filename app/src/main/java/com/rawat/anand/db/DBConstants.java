package com.rawat.anand.db;

/**
 * Created by Anu on 20-01-2017.
 */

public final class DBConstants {

    public final static String DATABASE_NAME = "sacrt.db";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME_BUSSTOP = "busstop";
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_STOPNUMBER = "stopnumber";
    public final static String COLUMN_DESCRIPTION = "description";
    public final static String SELECT_ALL_QUERY = "select * from busstop;";

    public DBConstants() {
    }

}
