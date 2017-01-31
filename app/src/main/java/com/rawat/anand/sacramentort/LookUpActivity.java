package com.rawat.anand.sacramentort;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
                stop = DBUtility.getInstance().getStops().get(position);
                openEditActivity(busStopList.getContext());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean flag = DBUtility.getInstance().isNotUpdated();
        DBUtility.getInstance().loadDBStops(this);
        if (flag) {
            listViewAdapter.clear();
            listViewAdapter.addAll(DBUtility.getInstance().getStops());
        }
        if (notCreated)
            listViewAdapter.addAll(DBUtility.getInstance().getStops());
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
