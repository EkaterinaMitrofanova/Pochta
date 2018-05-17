package com.itis.pochta.view;

import android.support.annotation.StringRes;

public interface ViewListener {

    void setFragment(String tag);
    void setTitle(@StringRes int resId);
    void startLoading(boolean start);
}
