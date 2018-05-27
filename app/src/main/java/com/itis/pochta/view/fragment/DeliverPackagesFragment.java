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

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentOrdersBinding;
import com.itis.pochta.model.base.Order;
import com.itis.pochta.model.request.DeliverForm;
import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.model.response.DeliverResponse;
import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.adapter.DriverOrdersRvAdapter;
import com.itis.pochta.view.adapter.OrdersRvAdapter;
import com.itis.pochta.view.listener.DriverOrderListener;
import com.itis.pochta.view.listener.ViewListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DeliverPackagesFragment extends Fragment implements DriverOrderListener, BaseView<List<Order>> {

    private FragmentOrdersBinding binding;
    private ViewListener viewListener;
    private List<Order> orderList;
    private DeliverForm deliverForm;

    @Inject
    PackageRepository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_orders);

        repository.getDriverOrders().observe(
                this,
                ordersResponse -> fillViews(ordersResponse.getOrders()),
                status -> startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
        );

        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(new DriverOrdersRvAdapter(orderList, this));
    }

    public void deliver() {
        if (deliverForm == null) return;
        repository.deliver(deliverForm).observe(
                this,
                this::onSuccess,
                status -> startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    private void onSuccess(DeliverResponse deliverResponse) {
        if (deliverResponse.getStatus().equals("ok")) {
            try {
                for (Order order: orderList){
                    if (deliverForm.getTickets().contains(order.getTicket())){
                        orderList.remove(order);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            deliverForm = null;
        } else {
            Toast.makeText(getContext(), "Некоторые посылки не доставлены", Toast.LENGTH_LONG).show();
        }
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
        if (deliverForm.getTickets().contains(ticket)) {
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
