package sundanllc.thewalker;


import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by Sunnara on 2/27/2017.
 */



public class CurrentGame extends FragmentActivity implements OnMapReadyCallback {

    private static final int CLUE_AMT = 4;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 11;
    private ViewPager pager;
    private ClueSlideAdapter pagerAdapter;
    private Button here;
    private TextView clueNum, clueDesc, timer;
    private Handler timehandle = new Handler();
    private boolean onoff = false;
    private SystemClock sc;
    private long milli = 0L;
    private long start = 0L;
    private long pause = 0L;
    private int id, onCheck;
    private double x, y;
    private ArrayList<Checkpoint> cp;
    private GoogleMap gm;
    private Marker you;
    private LocationManager lm;
    private Criteria c;
    private Location location, startLoc;

    public CurrentGame() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_game_layout);
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        onCheck = 0;


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapArea);
        mapFragment.getMapAsync(this);


        GameHelper gh = new GameHelper(this);
        WalkerGame game = gh.getGame(id);
        cp = gh.getCheckpoints(id);
        x = cp.get(onCheck).getX();
        y = cp.get(onCheck).getY();
        startLoc = new Location("");
        startLoc.setLatitude(x);
        startLoc.setLongitude(y);
        pager = (ViewPager) findViewById(R.id.cluepager);
        pagerAdapter = new ClueSlideAdapter(getSupportFragmentManager(), cp.get(0).getHints());
        pagerAdapter.hintNum(0);
        pager.setAdapter(pagerAdapter);


        timer = (TextView) findViewById(R.id.timer);

        here = (Button) findViewById(R.id.cg_button);
        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onoff) {
                    callPermissions();
                    if (ActivityCompat.checkSelfPermission(CurrentGame.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(CurrentGame.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                        callPermissions();
                    }

                    Location yourLocation = gm.getMyLocation();
                    float distance = yourLocation.distanceTo(startLoc);
                    if(distance > 25) {
                        Toast t = Toast.makeText(CurrentGame.this,"Please go closer to the starting point (25m) .\n Current Distance is "
                                + distance + "m.",Toast.LENGTH_LONG);
                        t.show();
                        return;
                    }
                    here.setText("I'm here");
                    start = sc.uptimeMillis();
                    startTimer();
                    onoff = true;
                    you.remove();
                } else {
                    pause += milli;
                    timehandle.removeCallbacks(updateTime);
                    onoff = false;
                }
            }
        });

    }

    public void callPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;

        }
    }


    private int secs = 0, mins = 0, hours = 0;
    private float dis;
    private long update;
    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            milli = sc.uptimeMillis() - start;
            update = pause + milli;

            secs = (int) update / 1000;
            mins = secs / 60;
            hours = mins / 60;
            mins = mins % 60;
            secs = secs % 60;
            String time = String.format("%02d:%02d:%02d", hours, mins, secs);
            timer.setText(time);
            if (hours == 24) {
                Toast.makeText(CurrentGame.this, "Day 1 Over, game stopped", Toast.LENGTH_SHORT).show();
                return;
            }
            if (secs == 0 && onCheck > 0)
            {
                dis = location.distanceTo(startLoc);
                if ()
            }
            timehandle.postDelayed(this, 0);
        }
    };

    public void startTimer() {
        long hour, minute, seconds;
        timehandle.postDelayed(updateTime, 0);
        //timer.setText("started");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final Location[] yourPosition = new Location[1];
        LatLng ll = new LatLng(x, y);
        LatLng yourll;

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(ll).title("Starting location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_arrow_b)));
        marker.showInfoWindow();

        c = new Criteria();

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                yourPosition[0] = location;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;

        }

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String provider = String.valueOf(lm.getBestProvider(c, true));
        lm.requestLocationUpdates(provider, 1000, 0, locationListener);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        yourll = new LatLng(location.getLatitude(), location.getLongitude());
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,15.0f));
        gm = googleMap;
        you = googleMap.addMarker(new MarkerOptions()
                .position(yourll).title("You"));
        marker.showInfoWindow();
        gm.setMyLocationEnabled(true);


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(you.getPosition());
        builder.include(marker.getPosition());

        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 150);
        googleMap.moveCamera(cu);

    }

    private class ClueSlideAdapter extends FragmentStatePagerAdapter {

        private String[] checkpoints;
        private int hints;

        public ClueSlideAdapter(FragmentManager fm, String[] cps) {
            super(fm);
            checkpoints = cps;
        }

        public void hintNum(int num)
        {
            hints = num;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new ClueFragment(checkpoints[position], position, hints);
        }

        @Override
        public int getCount() {
            return CLUE_AMT;
        }
    }
}

