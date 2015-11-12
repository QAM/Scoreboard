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
public class GameFragment extends BaseFragment implements View.OnClickListener, PingPongGame.PingPongListener {

    private TextView p1s;
    private TextView p2s;
    private SharedPreferences sharedpreferences;
    private SettingParam sp;
    private TextView game_detail;
    private TextView player1;
    private TextView player2;
    private PingPongGame ppg;


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
        v.findViewById(R.id.game_reset).setOnClickListener(this);
        p1s = (TextView) v.findViewById(R.id.player1_score);
        p2s = (TextView) v.findViewById(R.id.player2_score);
        p1u.setOnClickListener(this);
        p1d.setOnClickListener(this);
        p2u.setOnClickListener(this);
        p2d.setOnClickListener(this);
        p1s.setText("0");
        p2s.setText("0");
        sharedpreferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
        TextView vwc = (TextView) v.findViewById(R.id.game_detail_wc);
        TextView vnp = (TextView) v.findViewById(R.id.game_detail_np);
        TextView vgw = (TextView) v.findViewById(R.id.game_detail_gw);
        game_detail = (TextView) v.findViewById(R.id.game_detail_whowin);
        sp = FileOper.getSetting(sharedpreferences);
        vwc.setText("winning condition best of "+String.valueOf(sp.wc));
        vnp.setText("number of points to change serving right "+String.valueOf(sp.np));
        vgw.setText("game winning score " + String.valueOf(sp.gw));

        reset_game();
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.player1_up){
            ppg.p1_add();
        }else if(id==R.id.player1_down){
            ppg.p1_delete();
        }else if(id==R.id.player2_up){
            ppg.p2_add();
        }else if(id==R.id.player2_down){
            ppg.p2_delete();
        }else if(id==R.id.player1_name){
            showEditDialog(new EditNameDialog.EditNameDialogListener(){
                @Override
                public void onFinishEditDialog(String inputText) {
                    ppg.p1_set_name(inputText);
                    update_game_detail(ppg);
                }
            });
        }else if(id==R.id.player2_name){
            showEditDialog(new EditNameDialog.EditNameDialogListener(){
                @Override
                public void onFinishEditDialog(String inputText) {
                    ppg.p2_set_name(inputText);
                    update_game_detail(ppg);
                }
            });
        }else if(id==R.id.game_reset){
            reset_game();
        }

    }

    private void reset_game(){
        ppg = new PingPongGame(player1.getText().toString(), player2.getText().toString(), sp);
        ppg.setListener(this);
        update_game_detail(ppg);
    }

    private void showEditDialog(EditNameDialog.EditNameDialogListener l) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        EditNameDialog editNameDialog = EditNameDialog.newInstance("Input Name");
        editNameDialog.setListener(l);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    private void update_game_detail(PingPongGame ppg){
        if(ppg.getWinner()!=null) game_detail.setText(ppg.getWinner()+" win the game");
        else game_detail.setText(ppg.get_p1_name()+": "+String.valueOf(ppg.get_p1_win_count())+", "
                +ppg.get_p2_name()+": "+String.valueOf(ppg.get_p2_win_count())+"\n"
                +"Next One Serving: "+ppg.nextOne());
        p1s.setText(String.valueOf(ppg.get_p1_score()));
        p2s.setText(String.valueOf(ppg.get_p2_score()));
        player1.setText(ppg.get_p1_name());
        player2.setText(ppg.get_p2_name());
    }

    @Override
    public void onDataFinished() {
        update_game_detail(ppg);
    }
}
