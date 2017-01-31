package com.rawat.anand.sacramentort;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.rawat.anand.db.BusStop;
import com.rawat.anand.db.DBConstants;
import com.rawat.anand.db.DBHandler;
import com.rawat.anand.db.DBUtility;

public class EditStopActivity extends AppCompatActivity {
    EditText editActStopNumberEV = null;
    EditText editActDescEV = null;
    Integer stopId;
    DBHandler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        String stopNumber = bundle.get(Constants.STOP_NUMBER_MESSAGE).toString();
        String stopDesc = bundle.get(Constants.STOP_DESC_MESSAGE).toString();
        stopId = (Integer) bundle.get(Constants.STOP_ID_MESSAGE);
        editActStopNumberEV = (EditText) findViewById(R.id.editActStopNumberEV);
        editActDescEV = (EditText) findViewById(R.id.editActStopDescEV);
        if (stopNumber != null)
            editActStopNumberEV.setText(stopNumber.trim());
        if (stopDesc != null)
            editActDescEV.setText(stopDesc.trim());
    }

    public void editBusStopAndFinish(View view) {
        String stop = editActStopNumberEV.getText().toString();
        String desc = editActDescEV.getText().toString();
        if (Utility.isNotEmptyEV("Stop Description", desc, this))
            if (Utility.isNotEmptyEV("Stop number", stop, this)) {
                handler = new DBHandler(this, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
                BusStop busStop = new BusStop(desc, Integer.parseInt(stop));
                busStop.set_id(stopId);
                if (handler.editBusStop(busStop)) {
                    editActStopNumberEV.setText("");
                    editActDescEV.setText("");
                    Utility.alertWithToast(Constants.SUCCESS_SAVING_DATA, this);
                    DBUtility.getInstance().setIsNotUpdated(true);
                    finish();
                } else
                    Utility.alertWithToast(Constants.ERROR_SAVING_DATA, this);
            }
    }

    public void delBusStopAndFinish(View view) {
        handler = new DBHandler(this, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
        if (handler.deleteBusStop(stopId)) {
            editActStopNumberEV.setText("");
            editActDescEV.setText("");
            Utility.alertWithToast(Constants.SUCCESS_DELETING_DATA, this);
            DBUtility.getInstance().setIsNotUpdated(true);
            finish();
        } else
            Utility.alertWithToast(Constants.ERROR_DELETING_DATA, this);
    }

}
