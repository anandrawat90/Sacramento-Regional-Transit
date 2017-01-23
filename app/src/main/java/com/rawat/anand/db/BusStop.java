package com.rawat.anand.db;

/**
 * Created by Anand Rawat on 20-01-2017.
 */

public class BusStop {
    private int _id;
    private int stopNumber;
    private String description;

    public BusStop() {
    }

    public BusStop(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public BusStop(String description, int stopNumber) {
        this.description = description;
        this.stopNumber = stopNumber;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    @Override
    public String toString() {
        return this.stopNumber + " - " + this.description;
    }
}
