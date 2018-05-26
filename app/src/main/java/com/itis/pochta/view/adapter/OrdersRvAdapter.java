package com.itis.pochta.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itis.pochta.databinding.ListItemOrderBinding;
import com.itis.pochta.model.base.Order;
import com.itis.pochta.view.listener.PickUpListener;

import java.util.List;

public class OrdersRvAdapter extends RecyclerView.Adapter<OrdersRvAdapter.ViewHolder>{

    private List<Order> orders;
    private PickUpListener itemListener;

    public OrdersRvAdapter(List<Order> orders, PickUpListener itemListener) {
        this.orders = orders;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemOrderBinding binding = ListItemOrderBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.binding.setOrder(order);
        holder.binding.actionAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener.pickUp(order.getTicket())){
                    ((Button)v).setText("Оставить");
                } else {
                    ((Button)v).setText("Забрать");
                }
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
        }
    }
}
