package com.rawat.anand.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand Rawat on 20-01-2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer createBusStopTable = new StringBuffer("CREATE TABLE ").append(DBConstants.TABLE_NAME_BUSSTOP)
                .append(" ( ").append(DBConstants.COLUMN_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DBConstants.COLUMN_STOPNUMBER).append(" INTEGER NOT NULL, ")
                .append(DBConstants.COLUMN_DESCRIPTION).append(" TEXT NOT NULL );");
        db.execSQL(createBusStopTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME_BUSSTOP);
        onCreate(db);
    }

    public boolean addBusStop(BusStop busStop) {
        boolean flag;
        ContentValues values = new ContentValues();
        values.put(DBConstants.COLUMN_STOPNUMBER, busStop.getStopNumber());
        values.put(DBConstants.COLUMN_DESCRIPTION, busStop.getDescription());
        SQLiteDatabase db = getWritableDatabase();
        flag = (db.insert(DBConstants.TABLE_NAME_BUSSTOP, null, values) >= 0);
        db.close();
        return flag;
    }

    public boolean deleteBusStop(int busStopID) {
        boolean flag;
        SQLiteDatabase db = getWritableDatabase();
        flag = db.delete(DBConstants.TABLE_NAME_BUSSTOP, DBConstants.WHERE_ID_CLAUSE + String.valueOf(busStopID), null) > 0;
        db.close();
        return flag;
    }

    public boolean editBusStop(BusStop busStop) {
        boolean flag;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COLUMN_STOPNUMBER, busStop.getStopNumber());
        values.put(DBConstants.COLUMN_DESCRIPTION, busStop.getDescription());
        flag = db.update(DBConstants.TABLE_NAME_BUSSTOP, values, DBConstants.WHERE_ID_CLAUSE + String.valueOf(busStop.get_id()), null) > 0;
        db.close();
        return flag;
    }

    public BusStop queryBusStopInfo(BusStop busStop) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        if (busStop.getStopNumber() != 0) {
            cursor = db.query(false, DBConstants.TABLE_NAME_BUSSTOP, new String[]{DBConstants.COLUMN_ID, DBConstants.COLUMN_STOPNUMBER, DBConstants.COLUMN_DESCRIPTION}, DBConstants.COLUMN_STOPNUMBER + " = ?", new String[]{String.valueOf(busStop.getStopNumber())}, null, null, null, null);
            if (cursor.moveToFirst()) {
                busStop.set_id(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_ID)));
                busStop.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_DESCRIPTION)));
            }
            cursor.close();
        } else if (busStop.getDescription() != null && !busStop.getDescription().isEmpty()) {
            cursor = db.query(false, DBConstants.TABLE_NAME_BUSSTOP, new String[]{DBConstants.COLUMN_ID, DBConstants.COLUMN_STOPNUMBER, DBConstants.COLUMN_DESCRIPTION}, DBConstants.COLUMN_DESCRIPTION + " = ?", new String[]{busStop.getDescription()}, null, null, null, null);
            if (cursor.moveToFirst()) {
                busStop.set_id(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_ID)));
                busStop.setStopNumber(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_STOPNUMBER)));
            }
            cursor.close();
        }
        db.close();
        return busStop;
    }

    public List<BusStop> getAllBusStops() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<BusStop> stops = null;
        Cursor cursor = db.rawQuery(DBConstants.SELECT_ALL_QUERY, null);
        if (cursor.moveToFirst()) {
            stops = new ArrayList<BusStop>();
            while (!cursor.isAfterLast()) {
                BusStop busStop = new BusStop();
                busStop.set_id(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_ID)));
                busStop.setStopNumber(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_STOPNUMBER)));
                busStop.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_DESCRIPTION)));
                stops.add(busStop);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return stops;
    }

}
