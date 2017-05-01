package sundanllc.thewalker;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationListener;
import android.location.Address;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/27/2017
 *          Description -
 */

public class CheckpointDialog extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 11;
    private GameHelper gh;
    private Checkpoint cp;
    private Button cancel, add;
    private TextView gpshere, mapopen, h1, h2, h3, h4;
    private String newX, newY;
    private EditText x, y;
    private int id;
    private Criteria c;
    private CheckpointAdapter cpa;
    private Location location;
    private List<Address> addresses;
    private LocationManager lm;
    private LocationListener ll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkpoint_dialog);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.80);
        int height = (int) (metrics.heightPixels * 0.80);
        getWindow().setLayout(width, height);

        Bundle extra = getIntent().getExtras();
        id = extra.getInt("id");
        cpa = extra.getParcelable("cpa");

        x = (EditText) findViewById(R.id.x_input);
        y = (EditText) findViewById(R.id.y_input);
        h1 = (EditText) findViewById(R.id.hint1_input);
        h2 = (EditText) findViewById(R.id.hint2_input);
        h3 = (EditText) findViewById(R.id.hint3_input);
        h4 = (EditText) findViewById(R.id.hint4_input);

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;

        }

        ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lm.removeUpdates(ll);
                newX = "" + location.getLatitude();
                newY = "" + location.getLongitude();
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

        c = new Criteria();
        String provider = String.valueOf(lm.getBestProvider(c,true));
        lm.requestLocationUpdates(provider,1000,0, ll);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        /**
        Geocoder g = new Geocoder(this);
        try {
            addresses = g.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }*/




        gpshere = (TextView) findViewById(R.id.currloc);
        gpshere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newX = "" + location.getLatitude();
                x.setText(newX);
                newY = "" + location.getLongitude();
                y.setText(newY);

                Geocoder g = new Geocoder(CheckpointDialog.this);
                try {
                    addresses = g.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        mapopen = (TextView) findViewById(R.id.open_map);
        mapopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckpointDialog.this, MapFragment.class);
                i.putExtra("location",location);
                startActivityForResult(i,0);

            }
        });

        gh = new GameHelper(this);

        add = (Button) findViewById(R.id.add_cp);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double xLat = Double.parseDouble(x.getText().toString());
                double yLong = Double.parseDouble(y.getText().toString());
                Geocoder g = new Geocoder(CheckpointDialog.this);
                try {
                    //addresses = g.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    addresses = g.getFromLocation(xLat,yLong,1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(CheckpointDialog.this,"Not a valid location",Toast.LENGTH_SHORT);
                    t.show();
                    return;

                }

                if(addresses.size() == 0 ) {
                    Toast t = Toast.makeText(CheckpointDialog.this,"Not a valid location",Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }

                boolean empty = x.getText().toString().trim().isEmpty() ||
                        y.getText().toString().trim().isEmpty() ||
                        h1.getText().toString().trim().isEmpty() ||
                        h2.getText().toString().trim().isEmpty() ||
                        h3.getText().toString().trim().isEmpty() ||
                        h4.getText().toString().trim().isEmpty();
                if(empty) {
                    Toast t = Toast.makeText(getApplicationContext(),"Missing inputs.", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                Float xl = Float.parseFloat(x.getText().toString());
                Float yl = Float.parseFloat(y.getText().toString());
                if((xl > 180 || xl < -180) || (yl > 180 || yl < -180)) {
                    Toast t = Toast.makeText(getApplicationContext(), "X and Y must be in a range of -180 to 180", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                cp = new Checkpoint();
                cp.setId(id);
                cp.setX(Float.parseFloat(x.getText().toString()));
                cp.setY(Float.parseFloat(y.getText().toString()));
                cp.setHint1(h1.getText().toString());
                cp.setHint2(h2.getText().toString());
                cp.setHint3(h3.getText().toString());
                cp.setHint4(h4.getText().toString());
                cp.setAddress(addresses.get(0).getAddressLine(0));
                gh.insertCheckpoint(cp);
                ArrayList<Checkpoint> checks = gh.getCheckpoints((int)cp.getId());
                ArrayList<Location> locs = new ArrayList<Location>();
                for (Checkpoint a : checks)
                {

                }


                cpa.updateDataset(gh.getCheckpoints((int) id));
                cpa.notifyDataSetChanged();
                finish();

            }
        });

        cancel = (Button) findViewById(R.id.cancel_cp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        gh = new GameHelper(this);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Location l = data.getParcelableExtra("location");
            x.setText("" + l.getLatitude());
            y.setText("" + l.getLongitude());
            Geocoder g = new Geocoder(this);
            try {
                addresses = g.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
