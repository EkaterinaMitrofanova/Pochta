package com.itis.pochta.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentOrdersBinding;
import com.itis.pochta.model.base.Order;
import com.itis.pochta.model.request.DeliverForm;
import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.adapter.DriverOrdersRvAdapter;
import com.itis.pochta.view.adapter.OrdersRvAdapter;
import com.itis.pochta.view.listener.DriverOrderListener;
import com.itis.pochta.view.listener.ViewListener;

import java.util.ArrayList;
import java.util.List;

public class DeliverPackagesFragment extends Fragment implements DriverOrderListener, BaseView<List<Order>>{

    private FragmentOrdersBinding binding;
    private ViewListener viewListener;
    private List<Order> orderList;
    private DeliverForm deliverForm;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_orders);

        //todo: Загрузить посылки водителя [22] . getDriverOrders в апи

        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(new DriverOrdersRvAdapter(orderList, this));
    }

    public void deliver(){
        //todo: Подтвердить доставку [8] deliver
    }

    private void onSuccess(){
        orderList = null;
        binding.rv.setAdapter(new DriverOrdersRvAdapter(orderList, this));
    }

    @Override
    public boolean deliver(long storageId, String ticket) {
        if (deliverForm == null) deliverForm = new DeliverForm();
        if (deliverForm.getTickets() == null) deliverForm.setTickets(new ArrayList<>());

        if (deliverForm.getStorageId() == -1) deliverForm.setStorageId(storageId);

        if (deliverForm.getStorageId() != storageId) {
            Toast.makeText(getActivity(), "Посылка для другого пункта", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (deliverForm.getTickets().contains(ticket)){
            deliverForm.getTickets().remove(ticket);
            if (deliverForm.getTickets().size() == 0) deliverForm.setStorageId(-1);
            return false;
        } else {
            deliverForm.getTickets().add(ticket);
            return true;
        }
    }

    @Override
    public void startLoading(boolean start) {
        viewListener.startLoading(start);
    }

    @Override
    public void fillViews(List<Order> o) {
        orderList = o;
        binding.rv.setAdapter(new DriverOrdersRvAdapter(orderList, this));
    }
}
