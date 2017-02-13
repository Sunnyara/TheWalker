package sundanllc.thewalker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sunnara on 2/8/2017.
 */

public class CreateAddOption extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapater;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button addCP, removeCP;
    private TextView addString, xString, yString;

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

    public class CheckpointAdapters extends RecyclerView.Adapter<CheckpointAdapters.ViewHolder> {
        private String[] mItemList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView addString, xString, yString;

            public ViewHolder (TextView v) {
                super(v);
            }


        }
    }
}
