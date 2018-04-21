package com.itis.pochta.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itis.pochta.R;
import com.itis.pochta.databinding.ActivityMapBinding;
import com.itis.pochta.model.base.Storage;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    public static final String KEY_ID = "com.itis.pochta.model.storage.id";
    public static final String KEY_ADDRESS = "com.itis.pochta.model.storage.address";

    private ActivityMapBinding binding;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_map);

        MapView mMapView = binding.map;
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);

        setSearchFragment();
    }

    private void setSearchFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                this.getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        if (autocompleteFragment == null) {
            Log.e("MapActivity", "setSearchFragment(): autocompleteFragment == null");
            return;
        }

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        autocompleteFragment.setFilter(filter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                String[] parts = place.getAddress().toString().split(", ");
                String country = parts[parts.length - 1];
                String city = place.getName().toString();
                //todo: Send country and city to get list of Storages

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));

                //----todo: Remove, just for test
                List<Storage> storages = new ArrayList<>();
                Storage storage = new Storage();
                storage.setId(1);
                storage.setAddress("Казань, Баумана улица, 82");
                storage.setName("Пункт: ТЦ Свита Холл");
                storage.setLat(55.787426);
                storage.setLon(49.120409);
                storages.add(storage);
                fillMap(storages);
                //----
            }

            @Override
            public void onError(Status status) {
                Log.e("MapActivity", status.toString());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMinZoomPreference(10);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(55.755826, 37.6172999)));
    }

    private void fillMap(List<Storage> storages){
        for (Storage storage : storages) {
            LatLng latLng = new LatLng(storage.getLat(), storage.getLon());
            mGoogleMap.setOnInfoWindowClickListener(this);
            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(storage.getName())
                    .snippet(storage.getAddress()));
            marker.setTag(storage);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Storage storage = (Storage) marker.getTag();
        if (storage != null) {
            Intent intent = new Intent();
            intent.putExtra(KEY_ID, storage.getId());
            intent.putExtra(KEY_ADDRESS, storage.getAddress());
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
