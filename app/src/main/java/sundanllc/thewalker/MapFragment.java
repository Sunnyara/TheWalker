package sundanllc.thewalker;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 4/8/2017
 *          Description -
 */

public class MapFragment extends AppCompatActivity implements OnMapReadyCallback {

    Location l;
    Marker marker;
    Button pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);


        Bundle extras = getIntent().getExtras();
        l = extras.getParcelable("location");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pin = (Button) findViewById(R.id.pin);
        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("location", l);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng current = new LatLng(l.getLatitude(), l.getLongitude());
        final MarkerOptions[] mo = {new MarkerOptions().position(current).title("Your location").draggable(true)};
        marker = googleMap.addMarker(mo[0]);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,14.0f));


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(marker !=  null) {
                    marker.remove();
                }
                mo[0] = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("Pin here?").draggable(true);
                l.setLatitude(latLng.latitude);
                l.setLongitude(latLng.longitude);
                marker = googleMap.addMarker(mo[0]);
            }
        });
    }
}
