package sundanllc.thewalker;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sunnara on 2/13/2017.
 */

public class CheckpointAdapter extends RecyclerView.Adapter<CheckpointAdapter.CheckpointHolder> {
    private ArrayList<Checkpoint> checkPoints;

    public CheckpointAdapter(ArrayList<Checkpoint> checkPoints) {
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
        //holder.addString.setText(cp.getmAddress());
        holder.xString.setText(Float.toString(cp.getX()));
        holder.yString.setText(Float.toString(cp.getY()));
        holder.bindCheckpoint(cp);
    }

    @Override
    public int getItemCount() {
        return checkPoints.size();
    }

    public void updateDataset(ArrayList<Checkpoint> cp) {
        this.checkPoints = cp;
        notifyDataSetChanged();
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