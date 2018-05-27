package com.itis.pochta.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.itis.pochta.view.adapter.PackageRvAdapter;
import com.itis.pochta.view.listener.ListItemListener;
import com.itis.pochta.view.listener.ViewListener;
import com.itis.pochta.view.view_models.TrackingFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class TrackingFragment extends Fragment implements BaseView<List<MyPackage>>, ListItemListener<String> {

    private FragmentTrackingBinding binding;
    private ViewListener viewListener;
    private int mPosition;
    private long storageId;
    private TrackingFragmentViewModel viewModel;

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

        viewModel = ViewModelProviders.of(this).get(TrackingFragmentViewModel.class);

        initViews();

        viewModel.getAcceptor().observe(
                this,
                acceptor -> {
                    if (acceptor != null) {
                        storageId = acceptor.getStorage();
                    }
                },
                status -> startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> {
                    viewModel.setNotLoaded();
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        return binding.getRoot();
    }

    private void initViews() {

        binding.rv.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(new PackageRvAdapter(null, this));

        binding.search.setOnClickListener(v -> {
            String phone = binding.searchPhone.getText().toString();
            repository.getPackagesByPhoneAndId(phone, storageId).observe(
                    this,
                    packagesListResponse -> {
                        List<MyPackage> myPackages = new ArrayList<>();
                        for (String s : packagesListResponse.getPackages()) {
                            repository.getMyPackage(s).observe(
                                    this,
                                    myPackage -> {
                                        myPackages.add(myPackage);
                                        binding.rv.getAdapter().notifyDataSetChanged();
                                    },
                                    status -> startLoading(status == ResponseLiveData.Status.LOADING),
                                    throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                        }
                        fillViews(myPackages);
                    },
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
    public void fillViews(List<MyPackage> myPackages) {
        ((PackageRvAdapter) binding.rv.getAdapter()).setPackages(myPackages);
    }

    private void onAcceptSuccess() {
        ((PackageRvAdapter) binding.rv.getAdapter()).update(mPosition);
    }

    @Override
    public void onItemClick(String ticket, int position) {
        mPosition = position;
        repository.acceptPackage(ticket).observe(
                this,
                aVoid -> onAcceptSuccess(),
                status -> startLoading(status == ResponseLiveData.Status.LOADING),
                throwable -> Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }
}
