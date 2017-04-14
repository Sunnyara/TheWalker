package sundanllc.thewalker;

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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 4/8/2017
 *          Description -
 */

public class MapFragment extends AppCompatActivity implements OnMapReadyCallback {

    Location l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);

        Bundle extras = getIntent().getExtras();
        l = extras.getParcelable("location");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng current = new LatLng(l.getLatitude(), l.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(current)
                .title("Your location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        googleMap.getMaxZoomLevel();
    }
}
