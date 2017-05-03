package sundanllc.thewalker;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sunnara on 2/13/2017.
 */

public class CheckpointAdapter extends RecyclerView.Adapter<CheckpointAdapter.CheckpointHolder> implements Parcelable{
    private ArrayList<Checkpoint> checkPoints;
    private Checkpoint cp;
    private boolean deleting;
    private View orig;
    private boolean start, finish;
    private Context con;
    private GameHelper checkHelper;

    public CheckpointAdapter(ArrayList<Checkpoint> checkPoints) {
        start = false;
        finish = false;
        this.checkPoints = checkPoints;
    }

    public CheckpointAdapter(ArrayList<Checkpoint> checkPoints, Context up) {
        start = false;
        finish = false;
        this.checkPoints = checkPoints;
        this.con = up;
        checkHelper = new GameHelper(con);
    }

    protected CheckpointAdapter(Parcel in) {
    }

    public static final Creator<CheckpointAdapter> CREATOR = new Creator<CheckpointAdapter>() {
        @Override
        public CheckpointAdapter createFromParcel(Parcel in) {
            return new CheckpointAdapter(in);
        }

        @Override
        public CheckpointAdapter[] newArray(int size) {
            return new CheckpointAdapter[size];
        }
    };

    public ArrayList<Long> getSelectedIds() {
        ArrayList<Long> ids = new ArrayList<Long>();
        for (Checkpoint c : checkPoints)
        {
            if(c.isSelect()) {
                ids.add(c.getCheckId());
            }
        }
        return ids;
    }
    @Override
    public CheckpointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_checkpoint_list_fragment, parent, false);
        return new CheckpointHolder(v);
    }

    @Override
    public void onBindViewHolder(final CheckpointHolder holder, int position) {
        final Checkpoint cp = checkPoints.get(position);
        if(cp.getType() == 0) {
            holder.flag.setImageResource(R.drawable.green);
        } else if (cp.getType() == 1) {
            holder.flag.setImageResource(R.drawable.white);
        } else if (cp.getType() == 2) {
            holder.flag.setImageResource(R.drawable.checker);
        }

        /**
        if(!deleting) {
            holder.object.setClickable(false);
            //holder.object.setBackgroundColor(orig.getResources().getColor(R.color.whitebg));
        }
        else
        {
            holder.object.setClickable(true);
            //holder.object.setBackgroundColor(orig.getResources().getColor(R.color.whitebg));
        }**/

        holder.addString.setText(cp.getAddress());
        holder.xString.setText(Float.toString(cp.getX()));
        holder.yString.setText(Float.toString(cp.getY()));
        holder.object.setBackgroundColor(orig.getResources().getColor(R.color.whitebg));
        final CheckpointHolder h = holder;
        holder.object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleting) {
                    if(!cp.isSelect()) {
                        cp.setSelect(true);
                        h.object.setBackgroundColor(orig.getResources().getColor(R.color.greybg));
                    } else
                    {
                        cp.setSelect(false);
                        h.object.setBackgroundColor(orig.getResources().getColor(R.color.whitebg));
                    }

                }
            }
        });
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

    public void delete(boolean deleting) {
        this.deleting = deleting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


    public class CheckpointHolder extends RecyclerView.ViewHolder {
        public TextView addString, xString, yString;
        public RelativeLayout object;
        public ImageView flag;
        private Checkpoint mCheckpoint;
        private int i = 1;

        public CheckpointHolder (View v) {
            super(v);
            orig = v;
            addString = (TextView) v.findViewById(R.id.address_info);
            xString = (TextView) v.findViewById(R.id.c_x);
            yString = (TextView) v.findViewById(R.id.c_y);
            object = (RelativeLayout) v.findViewById(R.id.checkpoint_object);
            flag = (ImageView) v.findViewById(R.id.flag);
            flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCheck();
                }
            });

        }

        public void bindCheckpoint(Checkpoint checkpoint) {
            mCheckpoint = checkpoint;
            i = mCheckpoint.getType();
            setCheck();
        }

        public void setCheck()
        {
            if (i == 0)
            {
                i = 1;
                start = false;
                flag.setImageResource(R.drawable.white);
                checkHelper.setType(i, (int) mCheckpoint.getCheckId());
            }
            else if (i == 1 && !finish)
            {
                i = 2;
                finish = true;
                flag.setImageResource(R.drawable.checker);
                checkHelper.setType(i, (int) mCheckpoint.getCheckId());
            }
            else if (i == 1 && finish && !start)
            {
                i = 0;
                start = true;
                flag.setImageResource(R.drawable.green);
                checkHelper.setType(i, (int) mCheckpoint.getCheckId());
            }
            else if (i == 1 && finish && start)
            {

            }
            else if (i == 2 && !start)
            {
                i = 0;
                finish = false;
                start = true;
                flag.setImageResource(R.drawable.green);
                checkHelper.setType(i, (int) mCheckpoint.getCheckId());
            }
            else if (i == 2 && start)
            {
                i = 1;
                finish = false;
                flag.setImageResource(R.drawable.white);
                checkHelper.setType(i, (int) mCheckpoint.getCheckId());
            }
        }





    }
}
