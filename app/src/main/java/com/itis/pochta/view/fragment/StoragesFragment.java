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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentStoragesBinding;
import com.itis.pochta.model.base.City;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.model.base.MyStorage;
import com.itis.pochta.model.base.Order;
import com.itis.pochta.util.CityAdapter;
import com.itis.pochta.view.BaseView;
import com.itis.pochta.view.listener.ViewListener;
import com.itis.pochta.view.adapter.StorageRvAdapter;
import com.itis.pochta.view.listener.ListItemListener;

import java.util.ArrayList;
import java.util.List;

public class StoragesFragment extends Fragment implements BaseView<List<MyStorage>>, ListItemListener<Long>{

    private FragmentStoragesBinding binding;
    private ViewListener viewListener;
    private List<City> cities;
    private AlertDialog progressDialog;
    private List<MyStorage> mStorages;
    private long cityId = -1;

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

        //todo: Загрузить список городов в cities

        if (mStorages == null){
            showCityDialog();
        } else {
            fillViews(mStorages);
        }
    }

    private void showCityDialog(){
        if (cities == null) return;
        AutoCompleteTextView textView = new AutoCompleteTextView(getActivity());
        textView.setAdapter(new CityAdapter(getActivity(), cities));

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityId = ((City) textView.getAdapter().getItem(position)).getId();
            }
        });
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(textView);
        dialog.setTitle("Выбор города");
        dialog.setCancelable(true);

        dialog.setPositiveButton("Готово", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if (cityId == -1) return;
                //todo: загрузить список пунктов. Айдишник города - cityId. После загрузки вызвать fillViews
            }
        });
        dialog.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        this.progressDialog = dialog.create();
        progressDialog.show();
    }

    private void setOrders(List<Order> orders){
        viewListener.startOrders(orders);
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
        //todo: Загрузить список посылок [6] (getOrders в api) и затем вызвать setOrders
    }
}
