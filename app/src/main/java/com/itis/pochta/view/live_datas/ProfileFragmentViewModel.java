package com.itis.pochta.view.live_datas;

import android.arch.lifecycle.ViewModel;

public class ProfileFragmentViewModel extends ViewModel {
    private boolean isFetced = false;

    public boolean isFetced() {
        return isFetced;
    }

    public void setFetced(boolean fetced) {
        isFetced = fetced;
    }
}
