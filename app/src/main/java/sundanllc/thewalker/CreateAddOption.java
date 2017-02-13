package sundanllc.thewalker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sunnara on 2/8/2017.
 */

public class CreateAddOption extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapater;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button addCP, removeCP;
    private TextView addString, xString, yString;

    private ArrayList<Checkpoint> cPArrayList;

    public CreateAddOption() {
        for(int i = 0; i == 100; i++) {
            cPArrayList.add(new Checkpoint(i,"address", "1923.233", "12392,442"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.create_screen_add_checkpoint);

        mRecyclerView = (RecyclerView) findViewById(R.id.checkpoint_create);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        addCP = (Button) findViewById(R.id.create_add_checkpoint);
        addCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        removeCP = (Button) findViewById(R.id.create_remove_checkpoint);
        removeCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




    public class CheckpointAdapters extends RecyclerView.Adapter<CheckpointAdapters.CheckpointHolder> {
        private String[] mItemList;
        private ArrayList<Checkpoint> checkPoints;

        public CheckpointAdapters(ArrayList<Checkpoint> checkPoints) {
            this.checkPoints = checkPoints;
        }

        @Override
        public CheckpointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.create_checkpoint_list_fragment, parent, false);

            return new CheckpointHolder(v);
        }

        @Override
        public void onBindViewHolder(CheckpointHolder holder, int position) {
            Checkpoint cp = checkPoints.get(position);
            holder.bindCheckpoint(cp);
        }

        @Override
        public int getItemCount() {
            return checkPoints.size();
        }

        public class CheckpointHolder extends RecyclerView.ViewHolder {
            public TextView addString, xString, yString;
            private Checkpoint mCheckpoint;

                public CheckpointHolder (View v) {
                    super(v);
                    addString = (TextView) v.findViewById(R.id.address_info);

                    xString = (TextView) v.findViewById(R.id.c_x);

                    yString = (TextView) v.findViewById(R.id.c_y);

            }

            public void bindCheckpoint(Checkpoint checkpoint) {
                mCheckpoint = checkpoint;

            }



        }
    }
}
