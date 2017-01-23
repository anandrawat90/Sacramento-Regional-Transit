package com.rawat.anand.sacrt.request;

import java.util.ArrayList;

/**
 * Created by Anu on 18-01-2017.
 */

public class ResponseMessage {
    private ArrayList<String> response;
    private boolean errorFlag;
    private String errorMessage;

    ResponseMessage() {
        response = null;
        errorFlag = false;
        errorMessage = null;
    }

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ArrayList<String> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<String> response) {
        this.response = response;
    }
}
