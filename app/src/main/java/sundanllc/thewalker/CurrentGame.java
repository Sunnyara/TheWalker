package sundanllc.thewalker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Sunnara on 2/27/2017.
 */



public class CurrentGame extends FragmentActivity implements OnMapReadyCallback {

    private static final int CLUE_AMT = 4;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 11;
    private ViewPager pager;
    private ClueSlideAdapter pagerAdapter;
    private Button here;
    private TextView timer;
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
    private Marker you ,st;
    private LocationManager lm;
    private Criteria c;
    private Location location, startLoc;
    private boolean origFilled,etaPost;
    private int checkpointPos = 1;

    private int secs = 0, mins = 0, hours = 0;
    private long update;
    private long eta;
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
            timehandle.postDelayed(this, 0);
        }
    };

    public boolean h1, h2 ,h3, h4;
    public Runnable etaCheck = new Runnable() {
        @Override
        public void run() {
            Toast t;
            if (eta <= milli) {
                if(!h4) {
                    pagerAdapter.hintNum(4);
                    t = Toast.makeText(CurrentGame.this, "Hint 4 Unlocked", Toast.LENGTH_SHORT);
                    pager.setAdapter(pagerAdapter);
                    pager.setCurrentItem(3);
                    pagerAdapter.notifyDataSetChanged();
                    h4 = true;
                    t.show();
                }
            } else if ((eta * (2.0 / 3.0)) <= milli) {
                if(!h3) {
                    pagerAdapter.hintNum(3);
                    t = Toast.makeText(CurrentGame.this, "Hint 3 Unlocked", Toast.LENGTH_SHORT);
                    pager.setAdapter(pagerAdapter);
                    pager.setCurrentItem(2);
                    pagerAdapter.notifyDataSetChanged();
                    h3 = true;
                    t.show();
                }
            } else if ((eta * (1.0 / 3.0)) <= milli) {
                if(!h2) {
                    pagerAdapter.hintNum(2);
                    t = Toast.makeText(CurrentGame.this, "Hint 2 Unlocked", Toast.LENGTH_SHORT);
                    pager.setAdapter(pagerAdapter);
                    pager.setCurrentItem(1);
                    pagerAdapter.notifyDataSetChanged();
                    h2 = true;
                    t.show();
                }
            } else {
                if(!h1) {
                    pagerAdapter.hintNum(1);
                    pager.setAdapter(pagerAdapter);
                    pager.setCurrentItem(0);
                    pagerAdapter.notifyDataSetChanged();
                    h1 = true;
                }
            }
            timehandle.postDelayed(this, 0);
        }
    };

    private Runnable follow = new Runnable() {
        @Override
        public void run() {
            LatLng ll = new LatLng(gm.getMyLocation().getLatitude(),gm.getMyLocation().getLongitude());
            CameraUpdate cu = CameraUpdateFactory.newLatLng(ll);
            gm.animateCamera(cu);
            timehandle.postDelayed(this,3000);
        }
    };

    public CurrentGame() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_game_layout);

        etaPost = false;

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        onCheck = 0;
        origFilled = false;
        etaPost = false;
        h1 = false;
        h2 = false;
        h3 = false;
        h4 = false;

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
        pagerAdapter.notifyDataSetChanged();



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
                    st.remove();
                    here.setKeepScreenOn(true);
                    timehandle.postDelayed(follow,3000);
                    startGame();
                } else {
                    pause += milli;
                    timehandle.removeCallbacks(updateTime);
                    onoff = false;
                }
            }
        });

    }


    public void startGame()
    {
        /** Necessary Startup stuff~ **/
        pagerAdapter = new ClueSlideAdapter(getSupportFragmentManager(), cp.get(checkpointPos).getHints());
        pager.setAdapter(pagerAdapter);
        Location yourPosition = gm.getMyLocation();
        Location cpPosition = new Location("");
        double cpX = cp.get(checkpointPos).getX();
        double cpY = cp.get(checkpointPos).getY();
        final double accuracy = yourPosition.getAccuracy();

        cpPosition.setLatitude(cpX);
        cpPosition.setLongitude(cpY);

        final float distance = yourPosition.distanceTo(cpPosition);
        /** Creates ETA once per checkpoints**/
        if(!origFilled) {
            //original = distance;
            eta = (long) ((distance / 2500) * 60 * 60 * 1000);
            eta += milli;
            origFilled = true;
            if(!etaPost) {
                timehandle.postDelayed(etaCheck, 0);
                etaPost = true;
            }
        }

        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkArea(accuracy, distance);
            }
        });
    }

    public void checkArea(double accuracy, double distance) {
        double distanceGoal;

        /** If checkpont position goes to size **/
        if((cp.size()-1)  == checkpointPos) {
            if (accuracy < 15) distanceGoal = 15;
            else if (accuracy < 75) distanceGoal = accuracy;
            else distanceGoal = 75;

            if (distanceGoal >= distance) {
                here.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        here.setText("Congratulations!");
                        timehandle.removeCallbacks(updateTime);
                        timehandle.removeCallbacks(etaCheck);
                        here.setKeepScreenOn(false);
                        here.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(CurrentGame.this, MainActivity.class);
                                startActivity(i);
                            }
                        });
                    }
                });
            } else {
                Toast t = Toast.makeText(CurrentGame.this, "You are not at the area", Toast.LENGTH_SHORT);
                t.show();
                return;
            }

            /** Checkpoint position less than size **/
        }
        else if((cp.size()-1) >= checkpointPos)
            {
                if(accuracy < 15) distanceGoal = 15;
                else if (accuracy < 75) distanceGoal = accuracy;
                else distanceGoal = 75;
                if(distanceGoal >= distance) {
                    LatLng temp = new LatLng(cp.get(checkpointPos).getX(),cp.get(checkpointPos).getY());
                    gm.addMarker(new MarkerOptions().position(temp).title("Checkpoint " + checkpointPos));
                    checkpointPos++;
                    Toast t = Toast.makeText(CurrentGame.this,"Move on to next Checkpoint " + checkpointPos ,Toast.LENGTH_SHORT);
                    origFilled = false;
                    t.show();
                    h1 = false;
                    h2 = false;
                    h3 = false;
                    h4 = false;
                    //timehandle.removeCallbacks(etaCheck);
                    here.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startGame();
                        }
                    });
                    startGame();
                } else {
                    Toast t = Toast.makeText(CurrentGame.this,"You are not at the area",Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
            }
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

    @Override
    public void onBackPressed() {
        timehandle.removeCallbacks(etaCheck);
        timehandle.removeCallbacks(updateTime);
        super.onBackPressed();
    }

    public void startTimer() {
        long hour, minute, seconds;
        timehandle.postDelayed(updateTime, 0);
        //timer.setText("started");
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final Location[] yourPosition = new Location[0];
        LatLng ll = new LatLng(x, y);
        final LatLng[] yourll = new LatLng[1];

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(ll).title("Starting location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_arrow_b)));
        marker.showInfoWindow();

        c = new Criteria();


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;

        }

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //yourPosition[0] = location;
                //yourll[0] = new LatLng(location.getLatitude(), location.getLongitude());

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


        st = marker;

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String provider = String.valueOf(lm.getBestProvider(c, true));
        lm.requestLocationUpdates(provider, 5000, 0, locationListener);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        yourll[0] = new LatLng(location.getLatitude(), location.getLongitude());
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,15.0f));




        gm = googleMap;
        you = googleMap.addMarker(new MarkerOptions()
                .position(yourll[0]).title("You"));
        marker.showInfoWindow();
        you.setVisible(false);
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
            ClueFragment cf = new ClueFragment(checkpoints[position], position, hints);
            return new ClueFragment(checkpoints[position], position, hints);
        }

        @Override
        public int getCount() {
            return CLUE_AMT;
        }
    }
}

