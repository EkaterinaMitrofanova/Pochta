package com.itis.pochta.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentStoragesBinding;
import com.itis.pochta.model.base.City;
import com.itis.pochta.model.base.MyStorage;
import com.itis.pochta.model.base.Order;
import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;
import com.itis.pochta.util.CityAdapter;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.adapter.StorageRvAdapter;
import com.itis.pochta.view.listener.ListItemListener;
import com.itis.pochta.view.listener.ViewListener;

import java.util.List;

import javax.inject.Inject;

public class StoragesFragment extends Fragment implements BaseView<List<MyStorage>>, ListItemListener<Long>{

    private FragmentStoragesBinding binding;
    private ViewListener viewListener;
    private List<City> cities;
    private AlertDialog progressDialog;
    private List<MyStorage> mStorages;
    private long cityId = -1;

    @Inject
    PackageRepository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_storages, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_storages);

        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.rv.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(new StorageRvAdapter(null, this));


        if (mStorages == null){
            repository.getCities().observe(
                    this,
                    citiesResponse -> {
                        cities = citiesResponse.getCities();
                        showCityDialog();
                    },
                    status -> startLoading(status == ResponseLiveData.Status.LOADING),
                    throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
            );
        } else {
            fillViews(mStorages);
        }
    }

    private void showCityDialog(){
        if (cities == null) return;
        AutoCompleteTextView textView = new AutoCompleteTextView(getActivity());
        textView.setAdapter(new CityAdapter(getActivity(), cities));

        textView.setOnItemClickListener((parent, view, position, id) -> cityId = ((City) textView.getAdapter().getItem(position)).getId());
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(textView);
        dialog.setTitle("Выбор города");
        dialog.setCancelable(true);

        dialog.setPositiveButton("Готово", (dialog1, arg1) -> {
            if (cityId == -1) return;
            repository.getStorages(cityId, null).observe(
                    StoragesFragment.this,
                    storagesResponse -> fillViews(storagesResponse.getMyStorages()),
                    status -> startLoading(status == ResponseLiveData.Status.LOADING),
                    throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });
        dialog.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        this.progressDialog = dialog.create();
        progressDialog.show();
    }

    private void setOrders(List<Order> orders, long storage) {
        viewListener.startOrders(orders, storage);
    }

    @Override
    public void startLoading(boolean start) {
        viewListener.startLoading(start);
    }

    @Override
    public void fillViews(List<MyStorage> o) {
        mStorages = o;
        ((StorageRvAdapter)binding.rv.getAdapter()).setStorages(o);
    }

    @Override
    public void onItemClick(Long storageId, int position) {
        repository.getOrders(storageId).observe(
                this,
                ordersResponse -> setOrders(ordersResponse.getOrders(), storageId),
                status -> startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }
}
