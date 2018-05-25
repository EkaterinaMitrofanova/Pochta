package com.itis.pochta.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.R;
import com.itis.pochta.databinding.ListItemPackageBinding;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.view.listener.PackageClickHandler;
import com.itis.pochta.view.listener.PackageListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PackageRvAdapter extends RecyclerView.Adapter<PackageRvAdapter.PackageHolder>{

    private List<MyPackage> packages;
    private PackageListener listener;

    public PackageRvAdapter(List<MyPackage> packages, PackageListener listener) {
        this.listener = listener;
        this.packages = packages;
    }

    @NonNull
    @Override
    public PackageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemPackageBinding binding = ListItemPackageBinding.inflate(inflater, parent, false);
        return new PackageHolder(binding.getRoot());
    }

    public void setPackages(List<MyPackage> packages) {
        this.packages = packages;
        notifyDataSetChanged();
    }

    public void update(int position){
        if (packages == null) return;
        packages.get(position).setStatus("Done");
        notifyItemChanged(position);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageHolder holder, int position) {
        MyPackage myPackage = packages.get(position);
        Date date = new Date(myPackage.getDate());
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        holder.binding.date.setText(formatForDateNow.format(date));

        holder.binding.setMypackage(myPackage);
        if (myPackage.isDone()){
            holder.binding.actionAccept.setVisibility(View.GONE);
        }
        holder.binding.setClick(new PackageClickHandler() {
            @Override
            public void onAcceptClick(View view) {
                listener.onItemClick(myPackage.getTicket(), position);
            }

            @Override
            public void onShowMoreClick(View view) {
                if (holder.binding.more.getVisibility() == View.VISIBLE){
                    holder.binding.more.setVisibility(View.GONE);
                    holder.binding.actionMore.setImageResource(R.drawable.ic_arrow_down);
                } else {
                    holder.binding.more.setVisibility(View.VISIBLE);
                    holder.binding.actionMore.setImageResource(R.drawable.ic_arrow_up);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (packages == null) return 0;
        return packages.size();
    }

    class PackageHolder extends RecyclerView.ViewHolder {

        ListItemPackageBinding binding;

        PackageHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
