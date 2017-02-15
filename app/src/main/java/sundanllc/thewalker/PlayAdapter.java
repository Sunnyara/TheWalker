package sundanllc.thewalker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static sundanllc.thewalker.R.id.titleView;

/**
 * Created by Daniel on 2/13/2017.
 */

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.ViewHolder> {

    private ArrayList<String[]> dataset;
    private int area;
    private Context c;

    public PlayAdapter(ArrayList<String[]> data, int area)
    {
        dataset = data;
        this.area = area;
    }

    @Override
    public PlayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_list_fragment, parent, false);
        inflatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area == 1) {
                    v.getContext().startActivity(new Intent(v.getContext(), CreateAddOption.class));
                }
            }
        });
        c = parent.getContext();
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PlayAdapter.ViewHolder holder, int position)
    {
        holder.titleView.setText(dataset.get(position)[0]);
        holder.authorView.setText(dataset.get(position)[1]);
        holder.etaView.setText(dataset.get(position)[2]);
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleView, authorView, etaView;


        public ViewHolder(View v)
        {
            super(v);
            titleView = (TextView) v.findViewById(R.id.titleView);
            authorView = (TextView) v.findViewById(R.id.authorView);
            etaView = (TextView) v.findViewById(R.id.etaView);
        }
    }
}
