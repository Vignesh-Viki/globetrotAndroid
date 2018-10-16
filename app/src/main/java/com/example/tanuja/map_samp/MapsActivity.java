package com.example.tanuja.map_samp;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    private GoogleMap mMap;
    //To store longitude and latitude from map
    private double longitude;
    private double latitude;
    private static String dist;
    private static String dur;

    //Buttons
    private ImageButton buttonCurrent;

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Initializing googleapi client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Initializing views and adding onclick listeners

        buttonCurrent = (ImageButton) findViewById(R.id.buttonCurrent);

        // buttonSave.setOnClickListener(this);
        buttonCurrent.setOnClickListener(this);
        // buttonView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }
    public void onSetFrom(View view)
    {
     // mMap.clear();
        EditText fromLoc = (EditText)findViewById(R.id.fromLoc);
        String from_loc = fromLoc.getText().toString();
        List<Address> addressList1 = null;
        if( from_loc != null && !from_loc.equals("") && from_loc.length()>2 ){
             try {
                addressList1 =  geo_coder1.getFromLocationName(from_loc,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address1 = addressList1.get(0);
            LatLng latLng1 = new LatLng(address1.getLatitude(),address1.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng1) //setting position
                    .title("From Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
           // Toast.makeText(getApplicationContext(),"k",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Enter From",Toast.LENGTH_SHORT).show();
        }
    }
    public void onSetTo(View view)
    {
          mMap.clear();
        EditText toLoc = (EditText)findViewById(R.id.toLoc);
        String to_loc = toLoc.getText().toString();
        List<Address> addressList2 = null;
        if( to_loc != null && !to_loc.equals("") &&  to_loc.length()>2 ){
            Geocoder geo_coder2 = new Geocoder(this);
            try {
                addressList2 =  geo_coder2.getFromLocationName(to_loc,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address2 = addressList2.get(0);
            LatLng latLng2 = new LatLng(address2.getLatitude(),address2.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng2) //setting position
                    .title("To Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));

        }
        else{
            Toast.makeText(getApplicationContext(),"Enter To",Toast.LENGTH_SHORT).show();
        }
    }

    public void onSearch(View view) {


/*GET 2ND TEXT LOCATION ENETERED*/
        EditText fromLoc = (EditText)findViewById(R.id.fromLoc);
        String from_loc = fromLoc.getText().toString();
        List<Address> addressList1 = null;
        if( from_loc != null && !from_loc.equals("") && from_loc.length()>2 ) {
            Geocoder geo_coder1 = new Geocoder(this);
            try {
                addressList1 = geo_coder1.getFromLocationName(from_loc, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address1 = addressList1.get(0);
            LatLng latLng1 = new LatLng(address1.getLatitude(), address1.getLongitude());


/*GET 2ND TEXT LOCATION ENETERED*/
        EditText toLoc = (EditText)findViewById(R.id.toLoc);
        String to_loc = toLoc.getText().toString();
        List<Address> addressList2 = null;
        if( to_loc != null && !to_loc.equals("") &&  to_loc.length()>2 ) {
            try {
                addressList2 = geo_coder2.getFromLocationName(to_loc, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address2 = addressList2.get(0);
            LatLng latLng2 = new LatLng(address2.getLatitude(), address2.getLongitude());

/*GET DISTANCE FORMULA*/

            double R = 6371;
            double dLat = (address2.getLatitude()-address1.getLatitude())*Math.PI/180;
            double dLon = (address2.getLongitude()-address1.getLongitude())*Math.PI/180;
            double lat1 = address1.getLatitude()*Math.PI/180;
            double lat2 = address2.getLatitude()*Math.PI/180;

            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double d = R * c * 1000;

/*URL BUILDING TO REQUEST ROUTE*/
                String url = getUrl(latLng1, latLng2);
                Log.d("onMapClick", url.toString());
                FetchUrl FetchUrl = new FetchUrl();

                // Start downloading json data from Google Directions API
                FetchUrl.execute(url);
                //display distance as toast
            mMap.setTrafficEnabled(true);
            Toast.makeText(getApplicationContext(),d+"km",Toast.LENGTH_SHORT).show();

        }}
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        //Adding a long click listener to the map
        mMap.setOnMapLongClickListener(this);
    }

    private void getCurrentLocation() {
        //Creating a location object
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }}
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {
        if(v == buttonCurrent){
            getCurrentLocation();
            
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();

        //Adding a new marker to the current pressed position we are also making the draggable true
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }
    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

private class FetchUrl extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... url) {

        // For storing data from web service
        String data = "";

        try {
            // Fetching the data from web service
            data = downloadUrl(url[0]);
            Log.d("Background Task data", data.toString());
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask();

        // Invokes the thread for parsing the JSON data
        parserTask.execute(result);

    }
}

private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {


    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;


        try {
            jObject = new JSONObject(jsonData[0]);
            Log.d("ParserTask", jsonData[0].toString());
            DataParser parser = new DataParser();
            Log.d("ParserTask", parser.toString());

            // Starts parsing data
            routes = parser.parse(jObject);
            Log.d("ParserTask","Executing routes");
            Log.d("ParserTask",routes.toString());
            //jLegs = ( (JSONObject)routes.get(0)).getJSONArray("legs");




        } catch (Exception e) {
            Log.d("ParserTask",e.toString());
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;
        String duration = "";

        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);


                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
                // Get duration from the list
                    duration = point.get("duration");


            }
            //Toast.makeText(getApplicationContext(),dist + "long",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),duration + "long",Toast.LENGTH_SHORT).show();

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            


            Log.d("onPostExecute","onPostExecute lineoptions decoded");

        }

        // Drawing polyline in the Google Map for the i-th route
        if(lineOptions != null) {
            mMap.addPolyline(lineOptions);
        }
        else {
            Log.d("onPostExecute","without Polylines drawn");
        }
    }
}}





