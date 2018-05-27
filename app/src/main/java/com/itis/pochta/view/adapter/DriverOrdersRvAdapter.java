package com.itis.pochta.view.adapter;

import android.arch.lifecycle.ViewModel;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itis.pochta.databinding.ListItemOrderBinding;
import com.itis.pochta.model.base.Order;
import com.itis.pochta.view.listener.DriverOrderListener;

import java.util.List;

public class DriverOrdersRvAdapter extends RecyclerView.Adapter<DriverOrdersRvAdapter.ViewHolder>{

    private List<Order> orders;
    private DriverOrderListener itemListener;

    public DriverOrdersRvAdapter(List<Order> orders, DriverOrderListener itemListener) {
        this.orders = orders;
        this.itemListener = itemListener;
    }

    public void setOrders(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemOrderBinding binding = ListItemOrderBinding.inflate(inflater, parent, false);
        return new DriverOrdersRvAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.binding.setOrder(order);
        holder.binding.actionAccept.setOnClickListener(v -> {
            if (itemListener.deliver(order.getDestination(), order.getTicket())){
                ((Button)v).setText("Отмена");
            } else {
                ((Button)v).setText("Выбрать");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orders == null) return 0;
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ListItemOrderBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.actionAccept.setText("Выбрать");
            binding.llValue.setVisibility(View.GONE);
            binding.vValue.setVisibility(View.GONE);
            binding.llWeight.setVisibility(View.GONE);
            binding.vWeight.setVisibility(View.GONE);
            binding.weight.setVisibility(View.GONE);
            binding.volume.setVisibility(View.GONE);
        }
    }

}
