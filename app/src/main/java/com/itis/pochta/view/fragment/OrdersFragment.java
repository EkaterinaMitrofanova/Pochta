package com.itis.pochta.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentOrdersBinding;
import com.itis.pochta.model.base.Order;
import com.itis.pochta.model.request.PickUpForm;
import com.itis.pochta.view.adapter.OrdersRvAdapter;
import com.itis.pochta.view.listener.ListItemListener;
import com.itis.pochta.view.listener.PickUpListener;
import com.itis.pochta.view.listener.ViewListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment implements PickUpListener{

    private FragmentOrdersBinding binding;
    private ViewListener viewListener;

    private List<String> pickUpList, leaveList;
    private List<Order> mOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_orders);

        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.rv.setAdapter(new OrdersRvAdapter(mOrders, this));
    }

    public void setOrders(List<Order> orders){
        mOrders = orders;
        leaveList = new ArrayList<>();
        for (Order order: orders){
            leaveList.add(order.getTicket());
        }
    }

    private void onPickUpSuccess(){
        viewListener.remove(getTag());
    }

    public void pickUp(){
        PickUpForm pickUpForm = new PickUpForm(pickUpList, leaveList);
        //todo: Подтверждение принятия посылок [7]. Метод pickUp в api. Затем вызвать onPickUpSuccess
    }

    @Override
    public boolean pickUp(String ticket) {
        if (pickUpList == null) pickUpList = new ArrayList<>();
        if (!pickUpList.contains(ticket)){
            pickUpList.add(ticket);
            leaveList.remove(ticket);
            return true;
        } else {
            pickUpList.remove(ticket);
            leaveList.add(ticket);
            return false;
        }
    }
}
