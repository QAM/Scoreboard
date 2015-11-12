package qam.scoreboard;

import qam.scoreboard.tools.SettingParam;
import java.util.Random;
/**
 * Created by qam on 11/11/15.
 */
public class PingPongGame {
    private final SettingParam sp;
    private boolean deuce;
    private String winner;
    private Player player1;
    private Player player2;
    private int firstplayer;
    private PingPongListener mListener;

    public interface PingPongListener {
        void onDataFinished();
    }

    class Player {
        public int score;
        public int win_count;
        public String name;

        public Player(String n) {
            score = 0;
            win_count = 0;
            name = n;
        }
    }

    public PingPongGame(String p1name, String p2name, SettingParam p) {
        sp = p;
        player1 = new Player(p1name);
        player2 = new Player(p2name);
        deuce = false;
        winner = null;
        Random rm = new Random(System.currentTimeMillis());

        //0: player1, 1: player2
        firstplayer = rm.nextInt(2);
    }

    public void setListener(PingPongListener l){
        mListener = l;
        mListener.onDataFinished();
    }

    public void p1_add(){
        add(player1);
    }
    public void p2_add(){
        add(player2);
    }
    public void p1_delete(){
        delete(player1);
    }
    public void p2_delete(){
        delete(player2);
    }

    public void p1_set_name(String name){
        if (winner != null) return;
        player1.name=name;
        mListener.onDataFinished();
    }
    public void p2_set_name(String name){
        if (winner != null) return;
        player2.name=name;
        mListener.onDataFinished();
    }

    public String nextOne(){
        int v=0;
        if(deuce)
            v=((player1.score-sp.np)+(player2.score-sp.np))%2;
        else
            v = ((player1.score+player2.score)/sp.np)%2;

        if(v==0){
            if(firstplayer==0) return player1.name;
            else return player2.name;
        }else{
            if(firstplayer==0) return player2.name;
            else return player1.name;
        }
    }
    public String getWinner(){
        return winner;
    }
    public String get_p1_name(){
        return player1.name;
    }
    public String get_p2_name(){
        return player2.name;
    }
    public int get_p1_win_count(){
        return player1.win_count;
    }
    public int get_p2_win_count(){
        return player2.win_count;
    }
    public int get_p1_score(){
        return player1.score;
    }
    public int get_p2_score(){
        return player2.score;
    }


    private void reset_score() {
        player1.score = 0;
        player2.score = 0;
        deuce=false;
    }

    private Player getOpposite(Player p) {
        if (p.equals(player1)) return player2;
        else return player1;
    }

    private void add(Player p) {
        if (winner != null) return;
        Player op = getOpposite(p);
        p.score++;
        if(!deuce){
            if (p.score == sp.gw) {
                p.win_count++;
                reset_score();
                if (p.win_count > (sp.wc / 2)) {
                    winner = p.name;
                }
            } else if ((p.score == (sp.gw - 1)) && p.score == op.score) {
                deuce = true;
            }
        }else{
            if((p.score-op.score)>=2){
                p.win_count++;
                reset_score();
                if (p.win_count > (sp.wc / 2)) {
                    winner = p.name;
                }
            }
        }
        mListener.onDataFinished();
    }

    private void delete(Player p) {
        if (winner != null) return;
        Player op = getOpposite(p);
        if (p.score == 0) return;
        else {
            if(!deuce){
                p.score--;
            }else{
                if(p.score>=op.score){
                    p.score--;
                }
                if(op.score==(sp.gw-1) && p.score<(sp.gw-1)) {
                    deuce = false;
                }
            }
        }
        mListener.onDataFinished();
    }
}
