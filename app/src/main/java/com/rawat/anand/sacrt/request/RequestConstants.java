package com.rawat.anand.sacrt.request;

public final class RequestConstants {
    public final static String STOP_TAG = "stop";
    public final static String PREDICTION_TAG = "pre";
    public final static String PREDICTION_TIME_TAG = "pt";
    public final static String PREDICTION_UNIT_TAG = "pu";
    public final static String MODE_TAG = "mode";
    public final static String FINAL_DESTINATION_TAG = "fd";
    public final static String VEHICLE_TAG = "v";
    public final static String ROUTE_DISPLAY_DESIGNATOR_TAG = "rd";
    public final static String NEXT_BUS_MINUTES_TAG = "nextbusminutes";
    public final static String NEXT_BUS_ON_ROUTINE_TAG = "nextbusonroutetime";
    public final static String NO_PREDICTION_MESSAGE_TAG = "noPredictionMessage";
    public final static String REQUEST_URL = "http://bustime.sacrt.com/bustime/eta/getStopPredictionsETA.jsp?route=all&stop=";
    public final static String REQUEST_KEY = "&key=";
    public final static int CONNECTION_TIMEOUT_MILLI_SEC = 2000;

    private RequestConstants() {
    }
}
