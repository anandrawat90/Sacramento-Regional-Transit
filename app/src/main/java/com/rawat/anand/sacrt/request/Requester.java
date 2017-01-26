package com.rawat.anand.sacrt.request;

import android.text.Html;

import com.rawat.anand.sacramentort.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Requester {

    public ResponseMessage getInfo(String[] busStops) {
        Transport[] transports;
        ResponseMessage message = new ResponseMessage();
        ArrayList<String> responses = null;
        URL requestURL;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        for (String busStop : busStops) {
            if (!busStop.isEmpty() && busStop != null) {
                Double key = Math.random();
                try {
                    requestURL = new URL(RequestConstants.REQUEST_URL + busStop + RequestConstants.REQUEST_KEY + key);
                    URLConnection conn = requestURL.openConnection();
                    conn.setConnectTimeout(RequestConstants.CONNECTION_TIMEOUT_MILLI_SEC);
                    conn.connect();
                    responses = new ArrayList<String>();
                    InputStream inputStream = conn.getInputStream();
                    docBuilder = dbFactory.newDocumentBuilder();
                    Document doc = docBuilder.parse(inputStream);
                    doc.getDocumentElement().normalize();
                    NodeList noPredictionList = doc.getElementsByTagName(RequestConstants.NO_PREDICTION_MESSAGE_TAG);
                    if (noPredictionList != null && noPredictionList.getLength() > 0) {
                        message.setErrorFlag(true);
                        Element noPrediction = (Element) noPredictionList.item(0);
                        message.setErrorMessage(Html.fromHtml(noPrediction.getTextContent()).toString().trim().toUpperCase());
                    } else {
                        NodeList predictionList = doc.getElementsByTagName(RequestConstants.PREDICTION_TAG);
                        transports = new Transport[predictionList.getLength()];
                        for (int i = 0; i < predictionList.getLength(); i++) {
                            transports[i] = new Transport();
                            Element prediction = (Element) predictionList.item(i);
                            transports[i].setRoute(Html.fromHtml(prediction.getElementsByTagName(RequestConstants.ROUTE_DISPLAY_DESIGNATOR_TAG).item(0).getTextContent()).toString());
                            transports[i].setDestination(Html.fromHtml(prediction.getElementsByTagName(RequestConstants.FINAL_DESTINATION_TAG).item(0).getTextContent()).toString());
                            transports[i].setTime(Html.fromHtml(prediction.getElementsByTagName(RequestConstants.PREDICTION_TIME_TAG).item(0).getTextContent() + prediction.getElementsByTagName(RequestConstants.PREDICTION_UNIT_TAG).item(0).getTextContent()).toString());
                            transports[i].setExpectedTime(Html.fromHtml(prediction.getElementsByTagName(RequestConstants.NEXT_BUS_ON_ROUTINE_TAG).item(0).getTextContent()).toString());
                            //System.out.println(transports[i]);
                            responses.add(transports[i].toString());
                        }
                    }
                    doc = null;
                    conn = null;
                    docBuilder = null;
                    requestURL = null;
                } catch (SocketTimeoutException e) {
                    message.setErrorFlag(true);
                    message.setErrorMessage(Constants.SOCKET_TIMEOUT_ERROR);
                } catch (IOException e) {
                    message.setErrorFlag(true);
                    message.setErrorMessage(e.getMessage());
                    //e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    message.setErrorFlag(true);
                    message.setErrorMessage(e.getMessage());
                    //e.printStackTrace();
                } catch (SAXException e) {
                    message.setErrorFlag(true);
                    message.setErrorMessage(e.getMessage());
                    //e.printStackTrace();
                } catch (Exception e) {
                    message.setErrorFlag(true);
                    message.setErrorMessage(e.getMessage());
                }

            }
        }
        message.setResponse(responses);
        return message;
    }

}
