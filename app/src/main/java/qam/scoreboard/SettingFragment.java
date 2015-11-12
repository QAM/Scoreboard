package qam.scoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import qam.scoreboard.tools.FileOper;
import qam.scoreboard.tools.SettingParam;

/**
 * Created by qam on 11/11/15.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener{
    private SettingParam sp;
    private RadioGroup mwc;
    private RadioGroup mnp;
    private RadioGroup mgw;
    SharedPreferences sharedpreferences;

    @Override
    protected int getFragmentId() {
        return R.id.nav_setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        mwc = (RadioGroup) v.findViewById(R.id.radio_group_wc);
        mnp = (RadioGroup) v.findViewById(R.id.radio_group_np);
        mgw = (RadioGroup) v.findViewById(R.id.radio_group_gw);
        v.findViewById(R.id.radio_wc3).setOnClickListener(this);
        v.findViewById(R.id.radio_wc5).setOnClickListener(this);
        v.findViewById(R.id.radio_wc7).setOnClickListener(this);
        v.findViewById(R.id.radio_np2).setOnClickListener(this);
        v.findViewById(R.id.radio_np5).setOnClickListener(this);
        v.findViewById(R.id.radio_gw11).setOnClickListener(this);
        v.findViewById(R.id.radio_gw21).setOnClickListener(this);
        sharedpreferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
        sp = FileOper.getSetting(sharedpreferences);
        initradios();
        return v;
    }

    private void initradios(){
        if(sp.wc==3){
            mwc.check(R.id.radio_wc3);
        }else if(sp.wc==5){
            mwc.check(R.id.radio_wc5);
        }else{
            mwc.check(R.id.radio_wc7);
        }

        if(sp.np==2){
            mnp.check(R.id.radio_np2);
        }else{
            mnp.check(R.id.radio_np5);
        }

        if(sp.gw==11){
            mgw.check(R.id.radio_gw11);
        }else{
            mgw.check(R.id.radio_gw21);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.radio_wc3:
                sp.wc=3;
                break;
            case R.id.radio_wc5:
                sp.wc=5;
                break;
            case R.id.radio_wc7:
                sp.wc=7;
                break;
            case R.id.radio_np2:
                sp.np=2;
                break;
            case R.id.radio_np5:
                sp.np=5;
                break;
            case R.id.radio_gw11:
                sp.gw=11;
                break;
            case R.id.radio_gw21:
                sp.gw=21;
                break;
        }
        FileOper.storeSetting(sharedpreferences, sp);
    }
}
