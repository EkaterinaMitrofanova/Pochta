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

import com.itis.pochta.model.base.MyStorage;

import java.util.ArrayList;
import java.util.List;

public class StorageAdapter extends ArrayAdapter<MyStorage> {

    private List<MyStorage> storages;
    private List<MyStorage> storagesFiltered;
    private int size;

    public StorageAdapter(@NonNull Context context, @NonNull List<MyStorage> objects) {
        super(context, android.R.layout.simple_list_item_2, objects);
        storages = objects;
        if (storagesFiltered == null) storagesFiltered = objects;
        size = storagesFiltered.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyStorage storage = storagesFiltered.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(storage != null ? storage.getName() : null);

        ((TextView) convertView.findViewById(android.R.id.text2))
                .setText(storage != null ? storage.getAddress() : null);
        return convertView;
    }

    @Nullable
    @Override
    public MyStorage getItem(int position) {
        return storagesFiltered.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    String charString = constraint.toString().toLowerCase();
                    storagesFiltered.clear();
                    for(MyStorage c: storages){
                        if(c != null && (c.getName().toLowerCase().contains(charString)
                        || c.getAddress().toLowerCase().contains(charString))) {
                            storagesFiltered.add(c);
                        }
                    }
                    filterResults.values = storagesFiltered;
                    filterResults.count = storagesFiltered.size();
                    size = storagesFiltered.size();
                } else {
                    storagesFiltered = storages;
                }
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
