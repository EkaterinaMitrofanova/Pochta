package com.itis.pochta.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentTrackingBinding;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.ViewListener;
import com.itis.pochta.view.adapter.PackageRvAdapter;
import com.itis.pochta.view.listener.PackageListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class TrackingFragment extends Fragment implements BaseView<List<MyPackage>>, PackageListener{

    private FragmentTrackingBinding binding;
    private ViewListener viewListener;
    private int mPosition;

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

        binding.rv.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(new PackageRvAdapter(null, this));

        binding.search.setOnClickListener(v -> {
            String phone = binding.searchPhone.getText().toString();
            //todo: Список посылок по номеру. Перед этим сделать запрос на профиль acceptor-а для получения id пункта
//            repository.getMyPackage(ticket).observe(
//                    this,
//                    this::fillViews,
//                    status -> startLoading(status == ResponseLiveData.Status.LOADING),
//                    throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
//            );
        });
    }

    @Override
    public void startLoading(boolean start) {
        viewListener.startLoading(start);
    }

    @Override
    public void fillViews(List<MyPackage> myPackages){
        ((PackageRvAdapter)binding.rv.getAdapter()).setPackages(myPackages);
    }

    private void onAcceptSuccess(){
        ((PackageRvAdapter)binding.rv.getAdapter()).update(mPosition);
    }

    @Override
    public void onItemClick(String ticket, int position) {
        mPosition = position;
        //todo: Подтверждение выдачи [21]. Когда запрос пройдёт успешно, вызвать метод onAcceptSuccess()
    }
}
