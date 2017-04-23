package sundanllc.thewalker;


import android.os.Bundle;
import android.os.SystemClock;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Sunnara on 2/27/2017.
 */


/**
public class CurrentGame extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }5

    @Override
    public void onPause() {
        super.onPause();
    }
}

 */

public class CurrentGame extends FragmentActivity implements OnMapReadyCallback{

    private static final int CLUE_AMT = 4;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private Button here;
    private TextView clueNum, clueDesc, timer;
    private ArrayList<Clue> clues;
    private Clue clue;
    private Handler timehandle = new Handler();
    private boolean onoff = false;
    private SystemClock sc;
    private long milli = 0L;
    private long start = 0L;
    private long pause = 0L;
    private int id;
    private double x,y;
    private ArrayList<Checkpoint> cp;

    public CurrentGame() {
        /**
        clues = new ArrayList<>();
        clues.add(new Clue(0,"blank"));
        clues.add(new Clue(1,"blank"));
        clues.add(new Clue(2,"blank"));
        clues.add(new Clue(3,"blank"));
         **/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_game_layout);
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapArea);
        mapFragment.getMapAsync(this);


        GameHelper gh = new GameHelper(this);
        WalkerGame game = gh.getGame(id);
        cp = gh.getCheckpoints(id);
        x = cp.get(0).getX();
        y = cp.get(0).getY();
        pager = (ViewPager) findViewById(R.id.cluepager);
        pagerAdapter = new ClueSlideAdapter(getSupportFragmentManager(), cp.get(0).getHints());
        pager.setAdapter(pagerAdapter);

        /**
        clueNum = (TextView) findViewById(R.id.cluenum);
        clueDesc = (TextView) findViewById(R.id.cluedesc);
        clueNum.setText("Clue " + clues.get(0).getNumber() + "");
        clueDesc.setText(clues.get(0).getDescription());
        **/

        timer = (TextView) findViewById(R.id.timer);

        here = (Button) findViewById(R.id.cg_button);
        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                here.setText("I'm here");
                if(!onoff) {
                    start = sc.uptimeMillis();
                    startTimer();
                    onoff = true;
                } else {
                    pause += milli;
                    timehandle.removeCallbacks(updateTime);
                    onoff = false;
                }
            }
        });

    }


    private int secs = 0, mins = 0, hours = 0;
    private long update;
    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            milli = sc.uptimeMillis() - start;
            update =  pause + milli;

            secs = (int) update/1000;
            mins = secs/60;
            hours = mins/60;
            mins = mins%60;
            secs = secs%60;
            String time = String.format("%02d:%02d:%02d",hours,mins,secs);
            timer.setText(time);
            if(hours == 24) {
                Toast.makeText(CurrentGame.this, "Day 1 Over, game stopped", Toast.LENGTH_SHORT).show();
                return;
            }
            timehandle.postDelayed(this,0);
        }
    };

    public void startTimer() {
        long hour, minute, seconds;
        timehandle.postDelayed(updateTime,0);
        //timer.setText("started");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ll = new LatLng(x,y);
        googleMap.addMarker(new MarkerOptions().position(ll).title("Starting location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,18.0f));
    }

    private class ClueSlideAdapter extends FragmentStatePagerAdapter {

        private String[] checkpoints;

        public ClueSlideAdapter(FragmentManager fm, String[] cps) {
            super(fm);
            checkpoints = cps;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new ClueFragment(checkpoints[position], position);
        }

        @Override
        public int getCount() {
            return CLUE_AMT;
        }
    }
}

