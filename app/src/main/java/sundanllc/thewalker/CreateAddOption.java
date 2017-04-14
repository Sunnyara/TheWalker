package sundanllc.thewalker;

import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
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

    public CreateAddOption() {

    }

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


        /**
        for(int i = 0; i <= 100; i++) {
            cPArrayList.add(new Checkpoint(i,"Address v" + i, i, i, "hi", "how's", "it", "going"));
        }**/


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
                Dialog d = new CheckpointDialog(v.getContext(), checkpointAdapter, id);
                d.setTitle("Input here");
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                d.getWindow().setLayout((6 * width)/7, LinearLayout.LayoutParams.WRAP_CONTENT);
                d.show();
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
