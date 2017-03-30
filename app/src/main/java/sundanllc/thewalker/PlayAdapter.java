package sundanllc.thewalker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static sundanllc.thewalker.R.id.checkbox;
import static sundanllc.thewalker.R.id.titleView;

/**
 * Created by Daniel on 2/13/2017.
 */

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.ViewHolder> {

    private ArrayList<WalkerGame> dataset;
    private int area;
    private Context c;
    private boolean delete;

    public PlayAdapter(ArrayList<WalkerGame> data, int area)
    {
        dataset = data;
        this.area = area;
        delete = false;
    }

    @Override
    public PlayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_list_fragment, parent, false);
        inflatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area == 1) {
                    Intent gameinfo = (new Intent(v.getContext(), CreateAddOption.class));
                    TextView tv = (TextView) v.findViewById(R.id.posid);
                    gameinfo.putExtra("id", Integer.parseInt(String.valueOf(tv.getText())));
                    v.getContext().startActivity(gameinfo);
                }
                else
                {
                    Intent gameinfo = new Intent(v.getContext(), GameInfo.class);
                    TextView tv = (TextView) v.findViewById(R.id.posid);
                    gameinfo.putExtra("id", Integer.parseInt(String.valueOf(tv.getText())));
                    v.getContext().startActivity(gameinfo);
                }
            }
        });
        c = parent.getContext();
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PlayAdapter.ViewHolder holder, int position)
    {
        final WalkerGame wg = dataset.get(position);
        holder.titleView.setText(dataset.get(position).getTitle());
        holder.authorView.setText(dataset.get(position).getAuthor());
        holder.etaView.setText(Integer.toString(dataset.get(position).getEta()));
        holder.id.setText(Integer.toString(dataset.get(position).getId()));
        if (!delete)
        {
            holder.delBox.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.delBox.setVisibility(View.VISIBLE);
        }
        holder.delBox.setOnCheckedChangeListener(null);
        holder.delBox.setChecked(wg.getIsSelected());
        holder.delBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                wg.setSelected(b);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void delete(boolean toDelete)
    {
        this.delete = toDelete;
    }

    public ArrayList<Integer> getSelectedIds()
    {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (WalkerGame a : dataset)
        {
            if (a.getIsSelected())
            {
                ids.add(a.getId());
            }
        }
        return ids;
    }

    public void updateDataset(ArrayList<WalkerGame> games)
    {
        this.dataset = games;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleView, authorView, etaView, id;
        private CheckBox delBox;


        public ViewHolder(View v)
        {
            super(v);
            titleView = (TextView) v.findViewById(R.id.titleView);
            authorView = (TextView) v.findViewById(R.id.authorView);
            etaView = (TextView) v.findViewById(R.id.etaView);
            delBox = (CheckBox) v.findViewById(R.id.delBox);
            id = (TextView) v.findViewById(R.id.posid);
        }
    }
}
