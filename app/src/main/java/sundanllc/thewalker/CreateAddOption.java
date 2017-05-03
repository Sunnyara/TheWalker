package sundanllc.thewalker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    private RecyclerView.LayoutManager mLayoutManager;
    private GameHelper gh;
    private WalkerGame wg;
    private Checkpoint cp;
    private boolean deleted;

    private Button addCP, removeCP, finished;

    private ArrayList<Checkpoint> cPArrayList;
    private CheckpointAdapter checkpointAdapter;


    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.create_screen_add_checkpoint);

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt("id");

        //wg = gh.getGame(id);

        cPArrayList = new ArrayList<Checkpoint>();
        cp = new Checkpoint();
        gh = new GameHelper(this);
        cPArrayList = gh.getCheckpoints(id);

        wg = gh.getGame(id);
        if(cPArrayList.size() > 0) {
            wg = gh.getGame((int) cPArrayList.get(0).getId());
        }


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

        deleted = false;
        removeCP = (Button) findViewById(R.id.create_remove_checkpoint);
        removeCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deleted) {
                    deleted = true;
                    checkpointAdapter.delete(true);
                    checkpointAdapter.notifyDataSetChanged();
                    Drawable d = v.getResources().getDrawable(R.drawable.ic_delete);
                    removeCP.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
                }
                else
                {
                    deleted = false;
                    ArrayList<Long> ids = checkpointAdapter.getSelectedIds();
                    for(Long a : ids) {
                        gh.deleteCheckpoint(a);
                    }
                    checkpointAdapter.delete(false);
                    Drawable d = v.getResources().getDrawable(R.drawable.ic_remove);
                    removeCP.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
                    checkpointAdapter.updateDataset(gh.getCheckpoints(id));
                    checkpointAdapter.notifyDataSetChanged();
                }
            }
        });

        finished = (Button) findViewById(R.id.create_finished);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wg.setCreator(false);
                finish();
            }
        });
    }


}
