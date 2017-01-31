package com.rawat.anand.sacramentort;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.rawat.anand.db.BusStop;
import com.rawat.anand.db.DBUtility;
import com.rawat.anand.sacrt.request.Requester;
import com.rawat.anand.sacrt.request.ResponseMessage;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private String busStopNumber = null;
    private ResponseMessage message = null;
    private ArrayAdapter<String> listAdapter = null;
    private Toast errorInRequestToast = null;
    private AutoCompleteTextView homeBusStopEdit = null;
    private ListView homeBusListView = null;
    private CustomPredictionAdapter busStopPrediction = null;
    private Button homeActbutton = null;
    private ArrayList<BusStop> aux = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listAdapter = new ArrayAdapter<String>(this, R.layout.custom_list_item);
        homeBusListView = (ListView) findViewById(R.id.homeActBusList);
        homeBusListView.setAdapter(listAdapter);
        errorInRequestToast = Toast.makeText(homeBusListView.getContext(), Constants.MISSING_STOP_NUMBER_ERROR, Toast.LENGTH_LONG);
        homeBusStopEdit = (AutoCompleteTextView) findViewById(R.id.homeActBNEdit);
        DBUtility.getInstance().loadDBStops(this);
        aux = new ArrayList<>();
        aux.addAll(DBUtility.getInstance().getStops());
        busStopPrediction = new CustomPredictionAdapter(this, R.layout.stop_prediction_list_item, aux);
        homeBusStopEdit.setAdapter(busStopPrediction);
        homeBusStopEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        homeActbutton.callOnClick();
                        return true;
                    }
                }
                return false;
            }
        });
        homeBusStopEdit.setThreshold(1);
        homeActbutton = (Button) findViewById(R.id.homeActbutton);
        homeBusStopEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusStop stop = busStopPrediction.getItem(position);
                homeBusStopEdit.setText(String.valueOf(stop.getStopNumber()));
                homeActbutton.callOnClick();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DBUtility.getInstance().loadDBStops(this);
        busStopPrediction.setAllSavedStops(DBUtility.getInstance().getStops());
        busStopPrediction.clear();
        aux.clear();
        aux.addAll(DBUtility.getInstance().getStops());
        busStopPrediction.addAll(aux);
    }

    public void addCurrentStop(View view) {
        Intent addStopIntent = new Intent(this, AddBusStopActivity.class);
        busStopNumber = homeBusStopEdit.getText().toString().trim();
        addStopIntent.putExtra(Constants.STOP_NUMBER_MESSAGE, busStopNumber);
        startActivity(addStopIntent);
    }

    public void queryStopNumber(View view) {
        busStopNumber = homeBusStopEdit.getText().toString().trim();
        if (busStopNumber == null || busStopNumber.isEmpty())
            errorInRequestToast.show();
        else {
            Requester req = new Requester();
            message = req.getInfo(busStopNumber);
            listAdapter.clear();
            if (message != null && !message.isErrorFlag() && !message.getResponse().isEmpty())
                listAdapter.addAll(message.getResponse());
            else {
                errorInRequestToast.setText(message.getErrorMessage());
                errorInRequestToast.show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openLookUpActivity(View view) {
        Intent lookUpIntent = new Intent(this, LookUpActivity.class);
        startActivity(lookUpIntent);
    }
}
