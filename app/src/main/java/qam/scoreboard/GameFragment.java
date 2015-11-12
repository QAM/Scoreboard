package qam.scoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import qam.scoreboard.tools.FileOper;
import qam.scoreboard.tools.SettingParam;

/**
 * Created by qam on 11/11/15.
 */
public class GameFragment extends BaseFragment implements View.OnClickListener{

    private TextView p1s;
    private TextView p2s;
    private SharedPreferences sharedpreferences;
    private SettingParam sp;
    private TextView game_detail;
    private TextView player1;
    private TextView player2;


    @Override
    protected int getFragmentId() {
        return R.id.nav_game;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        ImageButton p1u = (ImageButton) v.findViewById(R.id.player1_up);
        ImageButton p1d = (ImageButton) v.findViewById(R.id.player1_down);
        ImageButton p2u = (ImageButton) v.findViewById(R.id.player2_up);
        ImageButton p2d = (ImageButton) v.findViewById(R.id.player2_down);
        player1 = (TextView) v.findViewById(R.id.player1_name);
        player2 = (TextView) v.findViewById(R.id.player2_name);
        player1.setOnClickListener(this);
        player2.setOnClickListener(this);

        p1s = (TextView) v.findViewById(R.id.player1_score);
        p2s = (TextView) v.findViewById(R.id.player2_score);
        p1u.setOnClickListener(this);
        p1d.setOnClickListener(this);
        p2u.setOnClickListener(this);
        p2d.setOnClickListener(this);
        p1s.setText("0");
        p2s.setText("0");
        sharedpreferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
        sp = FileOper.getSetting(sharedpreferences);
        TextView vwc = (TextView) v.findViewById(R.id.game_detail_wc);
        TextView vnp = (TextView) v.findViewById(R.id.game_detail_np);
        TextView vgw = (TextView) v.findViewById(R.id.game_detail_gw);
        game_detail = (TextView) v.findViewById(R.id.game_detail_whowin);
        vwc.setText("winning condition best of "+String.valueOf(sp.wc));
        vnp.setText("number of points to change serving right "+String.valueOf(sp.np));
        vgw.setText("game winning score "+String.valueOf(sp.gw));
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.player1_up){
            int value = Integer.valueOf(p1s.getText().toString());
            p1s.setText(String.valueOf(value+1));
        }else if(id==R.id.player1_down){
            int value = Integer.valueOf(p1s.getText().toString());
            p1s.setText(String.valueOf(value-1));
        }else if(id==R.id.player2_up){
            int value = Integer.valueOf(p2s.getText().toString());
            p2s.setText(String.valueOf(value+1));
        }else if(id==R.id.player2_down){
            int value = Integer.valueOf(p2s.getText().toString());
            p2s.setText(String.valueOf(value-1));
        }else if(id==R.id.player1_name){
            showEditDialog(new EditNameDialog.EditNameDialogListener(){
                @Override
                public void onFinishEditDialog(String inputText) {
                    player1.setText(inputText);
                    update_game_detail();
                }
            });
        }else if(id==R.id.player2_name){
            showEditDialog(new EditNameDialog.EditNameDialogListener(){
                @Override
                public void onFinishEditDialog(String inputText) {
                    player2.setText(inputText);
                    update_game_detail();
                }
            });
        }

    }

    private void showEditDialog(EditNameDialog.EditNameDialogListener l) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        EditNameDialog editNameDialog = EditNameDialog.newInstance("Input Name");
        editNameDialog.setListener(l);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    private void update_game_detail(){

    }

}
