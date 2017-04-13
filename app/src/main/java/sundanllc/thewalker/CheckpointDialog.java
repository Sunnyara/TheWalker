package sundanllc.thewalker;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import java.util.Map;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/27/2017
 *          Description -
 */

public class CheckpointDialog extends Dialog {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 11;
    private GameHelper gh;
    private Checkpoint cp;
    private Button cancel, add;
    private TextView gpshere, mapopen, h1, h2, h3, h4;
    private EditText x, y;

    public Checkpoint getCp() {
        return cp;
    }

    public void setCp(Checkpoint cp) {
        this.cp = cp;
    }

    public CheckpointDialog(@NonNull final Context context, final CheckpointAdapter cpa, final long id) {
        super(context);
        setContentView(R.layout.checkpoint_dialog);

        x = (EditText) findViewById(R.id.x_input);
        y = (EditText) findViewById(R.id.y_input);
        h1 = (EditText) findViewById(R.id.hint1_input);
        h2 = (EditText) findViewById(R.id.hint2_input);
        h3 = (EditText) findViewById(R.id.hint3_input);
        h4 = (EditText) findViewById(R.id.hint4_input);


        gpshere = (TextView) findViewById(R.id.currloc);
        gpshere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                    return;

                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                String newX = "" + location.getLatitude();
                x.setText(newX);
                String newY = "" + location.getLongitude();
                y.setText(newY);
            }
        });
        mapopen = (TextView) findViewById(R.id.open_map);
        mapopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MapFragment.class);
                context.startActivity(i);
            }
        });

        gh = new GameHelper(context);

        add = (Button) findViewById(R.id.add_cp);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean empty = x.getText().toString().trim().isEmpty() ||
                        y.getText().toString().trim().isEmpty() ||
                        h1.getText().toString().trim().isEmpty() ||
                        h2.getText().toString().trim().isEmpty() ||
                        h3.getText().toString().trim().isEmpty() ||
                        h4.getText().toString().trim().isEmpty();
                if(empty) {
                    Toast t = Toast.makeText(context,"Missing inputs.", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                Float xl = Float.parseFloat(x.getText().toString());
                Float yl = Float.parseFloat(y.getText().toString());
                if((xl > 180 || xl < -180) || (yl > 180 || yl < -180)) {
                    Toast t = Toast.makeText(context, "X and Y must be in a range of -180 to 180", Toast.LENGTH_SHORT);
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
                //cp.setAddress(" ");
                gh.insertCheckpoint(cp);

                cpa.updateDataset(gh.getCheckpoints((int) id));
                cpa.notifyDataSetChanged();
                cancel();

            }
        });

        cancel = (Button) findViewById(R.id.cancel_cp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        gh = new GameHelper(context);

    }
}
