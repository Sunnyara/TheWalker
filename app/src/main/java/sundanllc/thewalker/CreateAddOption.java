package sundanllc.thewalker;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sunnara on 2/8/2017.
 */

public class CreateAddOption extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapater;
    private RecyclerView.LayoutManager mLayoutManager;
    private GameHelper gh;
    private Checkpoint cp;

    private Button addCP, removeCP;

    private ArrayList<Checkpoint> cPArrayList;
    private CheckpointAdapter checkpointAdapter;


    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.create_screen_add_checkpoint);

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt("id");


        cPArrayList = new ArrayList<Checkpoint>();
        cp = new Checkpoint();
        gh = new GameHelper(this);
        cPArrayList = gh.getCheckpoints(id);


        checkpointAdapter = new CheckpointAdapter(cPArrayList);
        mRecyclerView = (RecyclerView) findViewById(R.id.checkpoint_create);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(checkpointAdapter);


        addCP = (Button) findViewById(R.id.create_add_checkpoint);
        addCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAddOption.this,CheckpointDialog.class);
                i.putExtra("id",id);
                i.putExtra("cpa", (Parcelable) checkpointAdapter);
                startActivity(i);

            }
        });

        removeCP = (Button) findViewById(R.id.create_remove_checkpoint);
        removeCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
