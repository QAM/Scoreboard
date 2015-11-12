package qam.scoreboard.tools;

import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by qam on 11/11/15.
 */
public class FileOper {

    public static void storeSetting(SharedPreferences sh, SettingParam sp){
        SharedPreferences.Editor editor = sh.edit();
        editor.putInt("wc", sp.wc);
        editor.putInt("np", sp.np);
        editor.putInt("gw", sp.gw);
        editor.commit();
    }

    public static SettingParam getSetting(SharedPreferences sh){
        SettingParam sp;
        sp = new SettingParam(sh.getInt("wc", 3), sh.getInt("np", 3), sh.getInt("gw", 3));
        return sp;
    }
}
