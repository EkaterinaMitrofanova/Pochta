package com.itis.pochta.view.listener;

import android.support.annotation.StringRes;

import com.itis.pochta.model.base.Order;

import java.util.List;

public interface ViewListener {

    void setFragment(String tag);
    void setTitle(@StringRes int resId);
    void startLoading(boolean start);

    void startOrders(List<Order> orders, long storage);
    void remove(String tag);
}
