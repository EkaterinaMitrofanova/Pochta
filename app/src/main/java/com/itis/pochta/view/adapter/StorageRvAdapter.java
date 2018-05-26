package com.itis.pochta.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.databinding.ListItemStorageBinding;
import com.itis.pochta.model.base.MyStorage;
import com.itis.pochta.view.listener.ListItemListener;

import java.util.List;

public class StorageRvAdapter extends RecyclerView.Adapter<StorageRvAdapter.ViewHolder>{

    private List<MyStorage> storages;
    private ListItemListener<Long> itemListener;

    public StorageRvAdapter(List<MyStorage> storages, ListItemListener<Long> itemListener) {
        this.storages = storages;
        this.itemListener = itemListener;
    }

    public void setStorages(List<MyStorage> storages){
        this.storages = storages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemStorageBinding binding = ListItemStorageBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyStorage storage = storages.get(position);
        holder.binding.setMystorage(storage);
        holder.itemView.setOnClickListener(v -> itemListener.onItemClick(storage.getId(), position));
    }

    @Override
    public int getItemCount() {
        if (storages == null) return 0;
        return storages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ListItemStorageBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
