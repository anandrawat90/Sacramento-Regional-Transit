package com.rawat.anand.sacramentort;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rawat.anand.db.BusStop;
import com.rawat.anand.db.DBUtility;

public class LookUpActivity extends AppCompatActivity {
    ArrayAdapter<BusStop> listViewAdapter = null;
    ListView busStopList = null;
    BusStop stop = null;
    boolean notCreated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewAdapter = new ArrayAdapter<BusStop>(this, R.layout.custom_list_item);
        busStopList = (ListView) findViewById(R.id.lookupActBusList);
        busStopList.setAdapter(listViewAdapter);
        busStopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stop = DBUtility.stops.get(position);
                openEditActivity(busStopList.getContext());
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean flag = DBUtility.isNotUpdated;
        DBUtility.loadDBStops(this);
        if (flag) {
            listViewAdapter.clear();
            listViewAdapter.addAll(DBUtility.stops);
        }
        if (notCreated)
            listViewAdapter.addAll(DBUtility.stops);
        notCreated = false;
    }

    public void openHomeActivity(View view) {
        Intent home = new Intent(this, HomeActivity.class);
        startActivity(home);
    }

    public void openEditActivity(Context context) {
        Intent editStopIntent = new Intent(context, EditStopActivity.class);
        editStopIntent.putExtra(Constants.STOP_NUMBER_MESSAGE, String.valueOf(stop.getStopNumber()));
        editStopIntent.putExtra(Constants.STOP_DESC_MESSAGE, stop.getDescription());
        editStopIntent.putExtra(Constants.STOP_ID_MESSAGE, new Integer(stop.get_id()));
        startActivity(editStopIntent);
    }

}
