package com.itis.pochta.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itis.pochta.R;
import com.itis.pochta.databinding.FragmentPackageBinding;
import com.itis.pochta.view.activity.MapActivity;

public class PackageFragment extends Fragment implements View.OnClickListener{

    private FragmentPackageBinding binding;
    public static final int CODE_MAP = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_package, container, false);

        initViews();
        return binding.getRoot();
    }

    private void initViews(){
        binding.actionChangeStorage.setOnClickListener(this);
        binding.actionPackage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_change_storage: {
                startActivityForResult(new Intent(getActivity(), MapActivity.class), CODE_MAP);
                break;
            }
            case R.id.action_package: {
                break;
            }
        }
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
                long storageId = data.getLongExtra(MapActivity.KEY_ID, -1); //todo: Send this id
                String address = data.getStringExtra(MapActivity.KEY_ADDRESS);
                binding.storage.setText(address);
                break;
            }
        }
    }
}
