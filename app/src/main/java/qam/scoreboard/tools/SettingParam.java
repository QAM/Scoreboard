package qam.scoreboard.tools;

/**
 * Created by qam on 11/11/15.
 */

/**
 * This class is for storing PingPong setting parameter
 *
 * @param wc         support changing winning condition best of 3(default), or 5, or 7
 *
 * @param np         support changing number of points to change serving right (2 or 5(default))
 *
 * @param gw         support changing game winning score (11(default) or 21)
 *
 * */
public class SettingParam {
    public int wc;
    public int np;
    public int gw;
    public SettingParam(int a, int b, int c){
        wc=a;
        np=b;
        gw=c;
    }
}
