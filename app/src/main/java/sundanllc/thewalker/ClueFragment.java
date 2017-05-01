package sundanllc.thewalker;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/17/2017
 *          Description -
 */

public class ClueFragment extends android.support.v4.app.Fragment{
    private static final int CLUE_AMT = 4;
    private TextView clueNum, clueDesc;
    private ImageView lockView;
    private int index, pos;
    private String hint;
    private int hintNum;

    //private ArrayList<Checkpoint> clues = new ArrayList<Checkpoint>();


    public ClueFragment(String hint, int pos, int hintNum) {
        this.hint = hint;
        this.pos = pos;
        this.hintNum = hintNum;
    }

    public ClueFragment()
    {

    }

    public static ClueFragment newInstance(int index) {
        Bundle args = new Bundle();
        ClueFragment cf = new ClueFragment();
        cf.index = index;
        return cf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clue_fragment,container, false);
        clueNum = (TextView) view.findViewById(R.id.cluenum);
        clueNum.setText(Integer.toString(pos + 1));
        clueDesc = (TextView) view.findViewById(R.id.cluedesc);
        clueDesc.setText(hint);
        if (pos<hintNum) {
            view.setAlpha((float) .25);
            clueDesc.setVisibility(View.GONE);
            lockView = (ImageView) view.findViewById(R.id.lockView);
            lockView.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
