package com.tourkiev.chernobyltours.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tourkiev.chernobyltours.ModelMarker;
import com.tourkiev.chernobyltours.R;
import com.tourkiev.chernobyltours.activities.DisplayPointActivity;
import com.tourkiev.chernobyltours.helpers.GPSTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import static com.tourkiev.chernobyltours.Constants.EXTRAS_DESCRIPTION;
import static com.tourkiev.chernobyltours.Constants.EXTRAS_TITLE;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mMap;
    Location currentLocation;
    GPSTracker gpsTracker;
    ArrayList<ModelMarker> modelMarkerArrayList;  // arrayList of gmap markers
    ArrayList<MarkerOptions> googleMapArrayList; // arrayList of models
    public static HashMap<String, ModelMarker> hashMap;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        gpsTracker = new GPSTracker(getContext());
        modelMarkerArrayList = new ArrayList<>();
        googleMapArrayList = new ArrayList<>();
        hashMap = new HashMap<String, ModelMarker>();

        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        return view;
    }

    private ArrayList<ModelMarker> setGeoInMap(ArrayList<ModelMarker> modelMakerArrayList) {// method returns an array of models of markers


        // first points check point
        modelMarkerArrayList.add(new ModelMarker(50.45148595,
                30.52208215, getString(R.string.tour_starting_point),
                getString(R.string.tour_starting_point_description),
                convertToBitmap(R.drawable.data),
                50));// Tour starting point
        modelMarkerArrayList.add(new ModelMarker(50.45046123,
                30.5239436,
                getString(R.string.kreschatic_street),
                getString(R.string.kreshatik_street_description),
                convertToBitmap(R.drawable.data1),
                75));// Kreschatik street
        modelMarkerArrayList.add(new ModelMarker(50.45846028,
                30.52656412,
                getString(R.string.podol),
                getString(R.string.podol_description),
                convertToBitmap(R.drawable.data2),
                100));// Podol
        modelMarkerArrayList.add(new ModelMarker(50.60230732,
                30.45289993,
                getString(R.string.novi_petrivtsi), getString(R.string.novi_petrivtsi_description),
                convertToBitmap(R.drawable.data3),
                100));// Novi Petrivtsy
        modelMarkerArrayList.add(new ModelMarker(50.77636424,
                30.3228879,
                getString(R.string.dymer),
                getString(R.string.dymer_description),
                convertToBitmap(R.drawable.data4), 200));// Dymer
        modelMarkerArrayList.add(new ModelMarker(50.80640933, 30.15102267,
                getString(R.string.katyuzhanka),
                getString(R.string.katyachanka_description),
                convertToBitmap(R.drawable.data5)));// Katyuzhanka
        modelMarkerArrayList.add(new ModelMarker(50.91928863, 29.90091741, getString(R.string.ivankiv), getString(R.string.ivankiv_description), convertToBitmap(R.drawable.data6)));// Ivankiv
        modelMarkerArrayList.add(new ModelMarker(51.11254837, 30.12176514, getString(R.string.dityatki_check_point), getString(R.string.dityatki_check_point_description), convertToBitmap(R.drawable.data7)));// Dityatki Check Point
        modelMarkerArrayList.add(new ModelMarker(51.12256969, 30.12187243, getString(R.string.thirty_k_zone), getString(R.string.thirty_k_zone_description), convertToBitmap(R.drawable.data8)));// 30k zone
        modelMarkerArrayList.add(new ModelMarker(51.253804, 30.184443, getString(R.string.zalesye_village), getString(R.string.zalesye_village_description), convertToBitmap(R.drawable.data9)));// Zalesye Village
        // ten point check point
        modelMarkerArrayList.add(new ModelMarker(51.26497619, 30.20884037, getString(R.string.chornobyl), getString(R.string.chornobyl_description), convertToBitmap(R.drawable.data10)));// Chornobyl
        modelMarkerArrayList.add(new ModelMarker(51.27234674, 30.22422016, getString(R.string.trumpeting_angel_of_chornobyl), getString(R.string.trumpeting_angel_of_chornobyl_description), convertToBitmap(R.drawable.data11)));// Trumpeting Angel of Chernobyl
        modelMarkerArrayList.add(new ModelMarker(51.28024628, 30.20818055, getString(R.string.monuments_to_liquidator), getString(R.string.monuments_to_liquidator_description), convertToBitmap(R.drawable.data12)));// Monument to the liquidators
        modelMarkerArrayList.add(new ModelMarker(51.28688467, 30.20294622, getString(R.string.robots), getString(R.string.robots_description), convertToBitmap(R.drawable.data13)));// Robots
        modelMarkerArrayList.add(new ModelMarker(51.27269577, 30.23734152, getString(R.string.elijah_church), getString(R.string.elijah_church_description), convertToBitmap(R.drawable.data14)));// Elijah church
        modelMarkerArrayList.add(new ModelMarker(51.35342519, 30.12482285, getString(R.string.radar_duga), getString(R.string.radar_duga_description), convertToBitmap(R.drawable.data15)));// Radar duga
        modelMarkerArrayList.add(new ModelMarker(51.35342519, 30.12482285, getString(R.string.kopachi_village), getString(R.string.kopachi_village_description), convertToBitmap(R.drawable.data16)));// Kopachi Village
        modelMarkerArrayList.add(new ModelMarker(51.37854448, 30.11360049, getString(R.string.first_part_of_reactor), getString(R.string.first_part_of_reactor_description), convertToBitmap(R.drawable.data17)));// 1st Part nuclear Power Plant
        modelMarkerArrayList.add(new ModelMarker(51.39031566, 30.0938648, getString(R.string.chernobyl_new_safe_confinement), getString(R.string.chernobyl_new_safe_confinement_description), convertToBitmap(R.drawable.data18)));// 19 Chernobyl New Safe Confinement
        modelMarkerArrayList.add(new ModelMarker(51.39129981, 30.10875911, getString(R.string.second_part_power_part), getString(R.string.second_part_power_plant), convertToBitmap(R.drawable.data19)));// 19 Chernobyl New Safe Confinement
        // twenty point check point
        modelMarkerArrayList.add(new ModelMarker(51.39486798, 30.06919384, getString(R.string.pripyat_town), getString(R.string.pripyat_town_descriptuion), convertToBitmap(R.drawable.data20)));//Pripyat town
        modelMarkerArrayList.add(new ModelMarker(51.40798684, 30.06644726, getString(R.string.pripyat_river_point), getString(R.string.pripyat_river_point_description), convertToBitmap(R.drawable.data21)));//Pripyat river point
        modelMarkerArrayList.add(new ModelMarker(51.40666174, 30.05779445, getString(R.string.centre), getString(R.string.centre_description), convertToBitmap(R.drawable.data22)));//Center
        modelMarkerArrayList.add(new ModelMarker(51.40762545, 30.05620122, getString(R.string.ferris_wheel), getString(R.string.ferris_wheel_description), convertToBitmap(R.drawable.data23)));//Ferris wheel
        modelMarkerArrayList.add(new ModelMarker(51.41031571, 30.05469918, getString(R.string.stadium_avangard), getString(R.string.stadium_avangard_description), convertToBitmap(R.drawable.data24)));//Stadium avangard
        modelMarkerArrayList.add(new ModelMarker(51.40670189, 30.04939377, getString(R.string.swimming_pool), getString(R.string.swimming_pool_description), convertToBitmap(R.drawable.data25)));//Swimming pool
        modelMarkerArrayList.add(new ModelMarker(51.40233816, 30.0425756, getString(R.string.jupiter_factory), getString(R.string.jupiter_factory_description), convertToBitmap(R.drawable.data26)));//Jupiter factory
        modelMarkerArrayList.add(new ModelMarker(51.40227123, 30.05153954, getString(R.string.police_station), getString(R.string.police_station_description), convertToBitmap(R.drawable.data27)));//Police station

        return modelMarkerArrayList;


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(getContext(), DisplayPointActivity.class);
        intent.putExtra(EXTRAS_TITLE, marker.getTitle());
        intent.putExtra(EXTRAS_DESCRIPTION, marker.getSnippet());

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

        // adding markers
        for (ModelMarker modelMarker : setGeoInMap(modelMarkerArrayList)) {
            // create marker
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(modelMarker.getLatitude(), modelMarker.getLongitude()))
                    .title(modelMarker.getTitle());

            // add a maker
            googleMapArrayList.add(marker);

            // draw a radius
            drawCircle(marker.getPosition(), googleMap, modelMarker.getRadius());

            hashMap.put(marker.getTitle(), modelMarker); // link each marker with it's model
            googleMap.addMarker(marker);

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
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                if (mMap != null) {
                    // camera animation to current position
                    //mMap.animateCamera(CameraUpdateFactory.newLatLng(loc));

                    // returns nearest marker and shows snackbar
                    getNearestMarker(googleMapArrayList, location);

                }
            }
        });


        //marker click listener
        googleMap.setOnMarkerClickListener(this);
    }

    private Bitmap convertToBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(),
                resId);
    }


    // draw a circle radius
    private void drawCircle(LatLng point, GoogleMap googleMap, double radius) {

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(radius);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        googleMap.addCircle(circleOptions);

    }

    private void scanIfInRadius(Marker mMarker, CircleOptions mCircle, float distance[]) {
        Location.distanceBetween(mMarker.getPosition().latitude,
                mMarker.getPosition().longitude, mCircle.getCenter().latitude,
                mCircle.getCenter().longitude, distance);

        if (distance[0] > mCircle.getRadius()) {
            //Do what you need

        }
    }

    private double distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return dist * meterConversion;
    }

    private void getNearestMarker(ArrayList<MarkerOptions> gMarkers, Location currentLocation) {


        HashMap<MarkerOptions, Float> hm = new HashMap<>();
        ArrayList<Float> distancesByFloat = new ArrayList<>();


        for (MarkerOptions markerOptions : gMarkers) {

            // arrayList of latitudes
            // temporary variable
            Location temp = new Location(markerOptions.getTitle());
            temp.setLatitude(markerOptions.getPosition().latitude);
            temp.setLongitude(markerOptions.getPosition().longitude);


            // hm by title and current distance to marker
            hm.put(markerOptions, currentLocation.distanceTo(temp));
            // arrayList of distances
            distancesByFloat.add(currentLocation.distanceTo(temp));
            // Log.d("Dist" + " " + markerOptions.getTitle(), markerOptions.getTitle()/*String.valueOf(currentLocation.distanceTo(temp))*/);
        }

        // sorting arrayList to get a float
        Collections.sort(distancesByFloat);

        MarkerOptions markerOptions = null;
        for (HashMap.Entry<MarkerOptions, Float> hashMap : hm.entrySet()) {

            if (Objects.equals(hashMap.getValue(), distancesByFloat.get(0))) {

                markerOptions = hashMap.getKey();
            }
        }
        // returning the nearest title
        //createSnackBar(markerOptions, mMap);
    }

    private void createSnackBar(final MarkerOptions markerOptions, final GoogleMap mMap) {
        Snackbar snackbar = Snackbar
                .make(getView(), "Nearest point is : " + markerOptions.getTitle(), Snackbar.LENGTH_INDEFINITE)
                .setAction(">", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
                    }
                });

        snackbar.show();
    }
}
