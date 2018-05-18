package com.itis.pochta.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.itis.pochta.model.base.City;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends ArrayAdapter<City>{

    private List<City> cities;
    private List<City> citiesFiltered;
    private int size;

    public CityAdapter(@NonNull Context context, @NonNull List<City> objects) {
        super(context, android.R.layout.simple_dropdown_item_1line, objects);
        cities = objects;
        if (citiesFiltered == null) citiesFiltered = objects;
        size = citiesFiltered.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        City city = citiesFiltered.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(city != null ? city.getName() : null);

        return convertView;
    }

    @Nullable
    @Override
    public City getItem(int position) {
        return citiesFiltered.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    String charString = constraint.toString();
                    citiesFiltered = new ArrayList<>();
                    for(City c: cities){
                        if(c != null && c.getName().toLowerCase().contains(charString.toLowerCase())) {
                            citiesFiltered.add(c);
                        }
                    }
                    filterResults.values = citiesFiltered;
                    filterResults.count = citiesFiltered.size();
                } else {
                    citiesFiltered = cities;
                }
                size = citiesFiltered.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return size;
    }
}
