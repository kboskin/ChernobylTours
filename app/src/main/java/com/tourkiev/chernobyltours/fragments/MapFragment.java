package com.tourkiev.chernobyltours.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tourkiev.chernobyltours.Constants;
import com.tourkiev.chernobyltours.ModelMarker;
import com.tourkiev.chernobyltours.R;
import com.tourkiev.chernobyltours.activities.DisplayPointActivity;
import com.tourkiev.chernobyltours.helpers.GPSTracker;

import java.util.ArrayList;

import static com.tourkiev.chernobyltours.Constants.EXTRAS_DESCRIPTION;
import static com.tourkiev.chernobyltours.Constants.EXTRAS_TITLE;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mMap;
    Location currentLocation;
    GPSTracker gpsTracker;

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            if (mMap != null) {
                // camera animation
                mMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
            }
        }
    };

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        gpsTracker = new GPSTracker(getContext());

        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        return view;
    }

    private ArrayList<ModelMarker> setGeoInMap() {// method returns an array of models of markers

        ArrayList<ModelMarker> modelMarkerArrayList = new ArrayList<>();
        // first points check point
        modelMarkerArrayList.add(new ModelMarker(50.45148595, 30.52208215, getString(R.string.tour_starting_point), getString(R.string.tour_starting_point_description)));// Tour starting point
        modelMarkerArrayList.add(new ModelMarker(50.45046123, 30.5239436, getString(R.string.kreschatic_street), getString(R.string.kreshatik_street_description)));// Kreschatik street
        modelMarkerArrayList.add(new ModelMarker(50.45846028, 30.52656412, getString(R.string.podol), getString(R.string.podol_description)));// Podol
        modelMarkerArrayList.add(new ModelMarker(50.60230732, 30.45289993, getString(R.string.novi_petrivtsi), getString(R.string.novi_petrivtsi_description)));// Novi Petrivtsy
        modelMarkerArrayList.add(new ModelMarker(50.77636424, 30.3228879, getString(R.string.dymer), getString(R.string.dymer_description)));// Dymer
        modelMarkerArrayList.add(new ModelMarker(50.80640933, 30.15102267, getString(R.string.katyuzhanka), getString(R.string.katyachanka_description)));// Katyuzhanka
        modelMarkerArrayList.add(new ModelMarker(50.91928863, 29.90091741, getString(R.string.ivankiv), getString(R.string.ivankiv_description)));// Ivankiv
        modelMarkerArrayList.add(new ModelMarker(51.11254837, 30.12176514, getString(R.string.dityatki_check_point), getString(R.string.dityatki_check_point_description)));// Dityatki Check Point
        modelMarkerArrayList.add(new ModelMarker(51.12256969, 30.12187243, getString(R.string.thirty_k_zone), getString(R.string.thirty_k_zone_description)));// 30k zone
        modelMarkerArrayList.add(new ModelMarker(51.253804, 30.184443, getString(R.string.zalesye_village), getString(R.string.zalesye_village_description)));// Zalesye Village
        // ten point check point
        modelMarkerArrayList.add(new ModelMarker(51.26497619, 30.20884037, getString(R.string.chornobyl), getString(R.string.chornobyl_description)));// Chornobyl
        modelMarkerArrayList.add(new ModelMarker(51.27234674, 30.22422016, getString(R.string.trumpeting_angel_of_chornobyl), getString(R.string.trumpeting_angel_of_chornobyl_description)));// Trumpeting Angel of Chernobyl
        modelMarkerArrayList.add(new ModelMarker(51.28024628, 30.20818055, getString(R.string.monuments_to_liquidator), getString(R.string.monuments_to_liquidator_description)));// Monument to the liquidators
        modelMarkerArrayList.add(new ModelMarker(51.28688467, 30.20294622, getString(R.string.robots), getString(R.string.robots_description)));// Robots
        modelMarkerArrayList.add(new ModelMarker(51.27269577, 30.23734152, getString(R.string.elijah_church), getString(R.string.elijah_church_description)));// Elijah church
        modelMarkerArrayList.add(new ModelMarker(51.35342519, 30.12482285, getString(R.string.radar_duga), getString(R.string.radar_duga_description)));// Radar duga
        modelMarkerArrayList.add(new ModelMarker(51.35342519, 30.12482285, getString(R.string.kopachi_village), getString(R.string.kopachi_village_description)));// Kopachi Village
        modelMarkerArrayList.add(new ModelMarker(51.37854448, 30.11360049, getString(R.string.first_part_of_reactor), getString(R.string.first_part_of_reactor_description)));// 1st Part nuclear Power Plant
        modelMarkerArrayList.add(new ModelMarker(51.39031566, 30.0938648, getString(R.string.chernobyl_new_safe_confinement), getString(R.string.chernobyl_new_safe_confinement_description)));// 19 Chernobyl New Safe Confinement
        modelMarkerArrayList.add(new ModelMarker(51.39129981, 30.10875911, getString(R.string.second_part_power_part), getString(R.string.second_part_power_plant)));// 19 Chernobyl New Safe Confinement
        // twenty point check point
        modelMarkerArrayList.add(new ModelMarker(51.39486798, 30.06919384, getString(R.string.pripyat_town), getString(R.string.pripyat_town_descriptuion)));//Pripyat town
        modelMarkerArrayList.add(new ModelMarker(51.40798684, 30.06644726, getString(R.string.pripyat_river_point), getString(R.string.pripyat_river_point_description)));//Pripyat river point
        modelMarkerArrayList.add(new ModelMarker(51.40666174, 30.05779445, getString(R.string.centre), getString(R.string.centre_description)));//Center
        modelMarkerArrayList.add(new ModelMarker(51.40762545, 30.05620122, getString(R.string.ferris_wheel), getString(R.string.ferris_wheel_description)));//Ferris wheel
        modelMarkerArrayList.add(new ModelMarker(51.41031571, 30.05469918, getString(R.string.stadium_avangard), getString(R.string.stadium_avangard_description)));//Stadium avangard
        modelMarkerArrayList.add(new ModelMarker(51.40670189, 30.04939377, getString(R.string.swimming_pool), getString(R.string.swimming_pool_description)));//Swimming pool
        modelMarkerArrayList.add(new ModelMarker(51.40233816, 30.0425756, getString(R.string.jupiter_factory), getString(R.string.jupiter_factory_description)));//Jupiter factory
        modelMarkerArrayList.add(new ModelMarker(51.40227123, 30.05153954, getString(R.string.police_station), getString(R.string.police_station_description)));//Police station

        return modelMarkerArrayList;


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(getContext(), DisplayPointActivity.class);
        intent.putExtra(EXTRAS_TITLE, marker.getTitle());
        Log.d("markert", marker.getTitle());
        intent.putExtra(EXTRAS_DESCRIPTION, marker.getSnippet());
        Log.d("markers", marker.getSnippet());
        intent.putExtra(Constants.EXTRAS_IMAGE, marker.getTitle());
        startActivity(intent);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        setUpMap(mMap);

    }

    private void setUpMap(GoogleMap googleMap) {
        // Enable MyLocation Layer of Google Map
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);


        // Get LocationManager object from System Service LOCATION_SERVICE
        //LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        //Toast.makeText(getContext(), "Map is set", Toast.LENGTH_LONG).show();

        // Create a criteria object to retrieve provider
        //Criteria criteria = new Criteria();
        //criteria.setAccuracy(10000);

        // Get the name of the best provider
        //String provider = locationManager.getBestProvider(criteria, true);

        // default value
        currentLocation = gpsTracker.getLocation();

        // Get Current Location
        //Location myLocation = locationManager.getLastKnownLocation(provider);
        //googleMap.setOnMyLocationChangeListener(this);

        //set map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = currentLocation.getLatitude();

        // Get longitude of the current location
        double longitude = currentLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
        googleMap.setOnMyLocationChangeListener(myLocationChangeListener);


        // adding markers
        for (ModelMarker modelMarker : setGeoInMap()) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(modelMarker.getLatitude(), modelMarker.getLongitude()))
                    .title(modelMarker.getTitle())
                    .snippet(modelMarker.getDescription()));

        }

        //marker click listener
        googleMap.setOnMarkerClickListener(this);
    }


}
