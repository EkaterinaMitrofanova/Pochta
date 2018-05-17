package com.itis.pochta.view;

public interface BaseView<T> {

    void startLoading(boolean start);
    void fillViews(T o);
}
