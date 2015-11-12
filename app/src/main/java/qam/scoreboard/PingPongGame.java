package qam.scoreboard;

import qam.scoreboard.tools.SettingParam;

/**
 * Created by qam on 11/11/15.
 */
public class PingPongGame {
    SettingParam sp;
    boolean deuce;
    String winner;
    Player player1;
    Player player2;
    int who_play;
    int count;
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
        who_play = 1;
        count = 0;
    }

    public void setListener(PingPongListener l){
        mListener = l;
    }

    private void reset_score() {
        player1.score = 0;
        player2.score = 0;
    }

    private Player getOpposite(Player p) {
        if (p.equals(player1)) return player2;
        else return player1;
    }

    private void add(Player p) {
        if (winner != null) return;
        Player op = getOpposite(p);
        p.score++;
        if (p.score == sp.gw) {
            p.win_count++;
            reset_score();
            if (p.win_count > (sp.wc / 2)) {
                winner = p.name;
            }
        } else if ((p.score == (sp.gw - 1)) && p.score == op.score) {
            deuce = true;
        }
        mListener.onDataFinished();
    }

    private void delete(Player p) {
        if (winner != null) return;
        Player op = getOpposite(p);
        if (p.score == 0) return;
        else {
            p.score--;
            if (p.score != op.score) {
                deuce = false;
            }
        }
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
    }
    public void p2_set_name(String name){
        if (winner != null) return;
        player2.name=name;
    }

    public String nextOne(){
        //TODO: return name
        return player1.name;
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


}
