package com.rawat.anand.sacramentort;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rawat.anand.db.BusStop;
import com.rawat.anand.db.DBConstants;
import com.rawat.anand.db.DBHandler;


public class AddBusStopActivity extends AppCompatActivity {
    private EditText addActStopNumberEV;
    private EditText addActStopDescEV;
    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus_stop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        String stopNumber = bundle.get(Constants.STOP_NUMBER_MESSAGE).toString();
        addActStopNumberEV = (EditText) findViewById(R.id.addActStopNumberEV);
        addActStopDescEV = (EditText) findViewById(R.id.addActStopDescEV);
        if (stopNumber != null)
            addActStopNumberEV.setText(stopNumber.trim());

    }

    public void addBusStopAndFinish(View view) {
        String stop = addActStopNumberEV.getText().toString();
        String desc = addActStopDescEV.getText().toString();
        if (isNotEmptyEV("Stop Description", desc))
            if (isNotEmptyEV("Stop number", stop)) {
                handler = new DBHandler(this, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
                BusStop busStop = new BusStop(desc, Integer.parseInt(stop));
                if (handler.addBusStop(busStop)) {
                    addActStopNumberEV.setText("");
                    addActStopDescEV.setText("");
                    alertWithToast(Constants.SUCCESS_SAVING_DATA);
                    finish();
                } else
                    alertWithToast(Constants.ERROR_SAVING_DATA);
            }
    }


    private boolean isNotEmptyEV(String source, String text) {
        if (text != null && !text.trim().isEmpty())
            return true;
        alertWithToast(source + Constants.EMPTY_STRING_ERROR);
        return false;
    }

    private void alertWithToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
