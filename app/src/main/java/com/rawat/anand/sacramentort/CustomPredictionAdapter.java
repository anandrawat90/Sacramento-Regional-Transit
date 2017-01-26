package com.rawat.anand.sacramentort;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.rawat.anand.db.BusStop;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Anand Rawat on 21-01-2017.
 */

public class CustomPredictionAdapter extends ArrayAdapter<BusStop> {
    private List<BusStop> allSavedStops;
    Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                ArrayList<BusStop> suggestions = new ArrayList<BusStop>();
                for (BusStop contender : allSavedStops) {
                    if (contender.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(contender);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<BusStop>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(allSavedStops);
            }
        }
    };

    public CustomPredictionAdapter(Context context, int resource, List<BusStop> objects) {
        super(context, resource, objects);
        allSavedStops = new ArrayList<BusStop>();
        if (!objects.isEmpty())
            allSavedStops.addAll(objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customRow = inflater.inflate(R.layout.stop_prediction_list_item, parent, false);
        BusStop stop = getItem(position);
        TextView stopNumberDescTV = (TextView) customRow.findViewById(R.id.stopNumberDescTV);
        stopNumberDescTV.setText(stop.toString());
        return customRow;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return customFilter;
    }

    public void setAllSavedStops(List<BusStop> allSavedStops) {
        this.allSavedStops = allSavedStops;
    }
}
