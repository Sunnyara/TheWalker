package sundanllc.thewalker;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        if(cp.getType() == 0) {
            holder.flag.setImageResource(R.drawable.green);
        } else if (cp.getType() == 1) {
            holder.flag.setImageResource(R.drawable.white);
        } else if (cp.getType() == 2) {
            holder.flag.setImageResource(R.drawable.checker);
        }
        holder.addString.setText(cp.getAddress());
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
        public ImageView flag;
        private Checkpoint mCheckpoint;
        private int i = 1;

        public CheckpointHolder (View v) {
            super(v);
            addString = (TextView) v.findViewById(R.id.address_info);
            xString = (TextView) v.findViewById(R.id.c_x);
            yString = (TextView) v.findViewById(R.id.c_y);


            flag = (ImageView) v.findViewById(R.id.flag);
            flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(i == 0) {
                        flag.setImageResource(R.drawable.green);
                        i = 2;
                    } else if (i == 1) {
                        flag.setImageResource(R.drawable.white);
                        i = 0;
                    } else if (i == 2) {
                        flag.setImageResource(R.drawable.checker);
                        i = 1;
                    }
                }
            });

        }

        public void bindCheckpoint(Checkpoint checkpoint) {
            mCheckpoint = checkpoint;

        }



    }
}