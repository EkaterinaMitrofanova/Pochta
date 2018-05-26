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
import com.itis.pochta.model.request.PickUpForm;
import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;
import com.itis.pochta.view.adapter.OrdersRvAdapter;
import com.itis.pochta.view.listener.PickUpListener;
import com.itis.pochta.view.listener.ViewListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrdersFragment extends Fragment implements PickUpListener {

    private FragmentOrdersBinding binding;
    private ViewListener viewListener;

    private List<String> pickUpList, leaveList;
    private List<Order> mOrders;
    @Inject
    PackageRepository repository;
    private long storage;

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
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rv.setAdapter(new OrdersRvAdapter(mOrders, this));
    }

    public void setOrders(List<Order> orders, long storage) {
        mOrders = orders;
        this.storage = storage;
        leaveList = new ArrayList<>();
        if (orders != null) {
            for (Order order : orders) {
                leaveList.add(order.getTicket());
            }
        }
    }

    private void onPickUpSuccess() {
        viewListener.remove(getTag());
    }

    public void pickUp() {
        PickUpForm pickUpForm = new PickUpForm(pickUpList, leaveList);
        repository.pickUp(pickUpForm, storage).observe(
                this,
                aVoid -> onPickUpSuccess(),
                status -> viewListener.startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public boolean pickUp(String ticket) {
        if (pickUpList == null) pickUpList = new ArrayList<>();
        if (!pickUpList.contains(ticket)) {
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
