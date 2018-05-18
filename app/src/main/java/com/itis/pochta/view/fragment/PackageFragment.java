package com.itis.pochta.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentPackageBinding;
import com.itis.pochta.model.base.City;
import com.itis.pochta.model.base.MyStorage;
import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.repository.PackageRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;
import com.itis.pochta.util.CityAdapter;
import com.itis.pochta.util.StorageAdapter;
import com.itis.pochta.view.ViewListener;
import com.itis.pochta.view.activity.MapActivity;
import com.itis.pochta.view.listener.AutoCompleteListener;

import java.util.List;

import javax.inject.Inject;

public class PackageFragment extends Fragment implements View.OnClickListener{

    private FragmentPackageBinding binding;
    private ViewListener viewListener;
    private PackageForm packageForm;

    @Inject
    PackageRepository repository;

    public static final int CODE_MAP = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_package, container, false);

        viewListener = (ViewListener) getActivity();
        viewListener.setFragment(getTag());
        viewListener.setTitle(R.string.title_package);

        repository.getCities().observe(
                this,
                citiesResponse -> setCities(citiesResponse.getCities()),
                status -> startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
        );

        initViews();
        return binding.getRoot();
    }

    private void initViews(){
        binding.actionChangeStorage.setOnClickListener(this);
        binding.actionPackage.setOnClickListener(this);

        packageForm = new PackageForm();

        binding.setClick(new AutoCompleteListener() {
            @Override
            public void onViewClick(View view) {
                ((AutoCompleteTextView)view).showDropDown();
            }
        });

        binding.city.setOnItemClickListener((parent, view, position, id) -> {
            binding.storage.setText("");
            binding.storage.clearListSelection();
            long cityId = ((City) binding.city.getAdapter().getItem(position)).getId();
            repository.getStorages(String.valueOf(cityId), null).observe(
                    this,
                    storagesResponse -> setStorages(storagesResponse.getMyStorages()),
                    status -> startLoading(status == ResponseLiveData.Status.LOADING),
                    throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });

        binding.storage.setOnItemClickListener((parent, view, position, id) -> {
            long storageId = ((MyStorage) parent.getAdapter().getItem(position)).getId();
            packageForm.setDest_id(storageId);
        });
    }

    public void setCities(List<City> cities) {
        binding.city.setAdapter(new CityAdapter(getActivity(), cities));
    }

    public void setStorages(List<MyStorage> storages){
        binding.storage.setAdapter(new StorageAdapter(getActivity(), storages));

        if (storages != null) {
            binding.storage.showDropDown();
        }
    }

    public void startLoading(boolean start){
        viewListener.startLoading(start);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_change_storage: {
                startActivityForResult(new Intent(getActivity(), MapActivity.class), CODE_MAP);
                break;
            }
            case R.id.action_package: {
                if (isFormValid()) {
                    repository.issuePackage(packageForm).observe(
                            this,
                            packageResponse -> Toast.makeText(getContext(), "Succesfully created: " + packageResponse.getTicket(), Toast.LENGTH_SHORT).show(),
                            status -> startLoading(status == ResponseLiveData.Status.LOADING),
                            throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }
                break;
            }
        }
    }

    public PackageForm getForm(){
        return packageForm;
    }

    private boolean isFormValid(){
        String s;
        boolean valid = true;
        s = binding.sender.getText().toString();
        if (!s.matches("8[\\d]{10}")){
            valid = false;
            binding.sender.setError(getString(R.string.error_invalid_phone));
        } else {
            packageForm.setConsumer_phone(s);
        }
        packageForm.setVolume(binding.volume.getText().toString());
        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CODE_MAP: {
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getActivity(), getString(R.string.error_storage_choice), Toast.LENGTH_SHORT).show();
                    return;
                }
                long storageId = data.getLongExtra(MapActivity.KEY_ID, -1);
                String address = data.getStringExtra(MapActivity.KEY_ADDRESS);
                binding.storage.setText(address);
                break;
            }
        }
    }
}
