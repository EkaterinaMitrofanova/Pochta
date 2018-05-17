package com.itis.pochta.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentTrackingBinding;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.ViewListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;


public class TrackingFragment extends Fragment implements BaseView<MyPackage>{

    private FragmentTrackingBinding binding;
    private ViewListener viewListener;

    @Inject
    PackageRepository repository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracking, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_tracking);

        initViews();

        return binding.getRoot();
    }

    private void initViews(){

        binding.search.setOnClickListener(v -> {
            String ticket = binding.searchTicket.getText().toString();
            repository.getMyPackage(ticket).observe(
                    this,
                    this::fillViews,
                    status -> startLoading(status == ResponseLiveData.Status.LOADING),
                    throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });
    }

    @Override
    public void startLoading(boolean start) {
        viewListener.startLoading(start);
    }

    @Override
    public void fillViews(MyPackage myPackage){
        binding.packageInfo.setVisibility(View.VISIBLE);
        binding.setMypackage(myPackage);
        Date date = new Date(myPackage.getDate());
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        binding.date.setText(formatForDateNow.format(date));
    }
}
