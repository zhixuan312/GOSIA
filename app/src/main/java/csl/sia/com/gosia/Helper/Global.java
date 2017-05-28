package csl.sia.com.gosia.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import csl.sia.com.gosia.R;

/**
 * Created by xuan on 22/4/17.
 */

public class Global {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static GoogleApiClient mGoogleApiClient;
    public static ArrayList<String> acceptList = new ArrayList<>();

    public static String loadJSON(Context context,String name) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("json/"+name+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int retrieveImageId(Character input) {
        switch (input) {
            case 'a':
            case 'A':
                return R.drawable.ic_a;
            case 'b':
            case 'B':
                return R.drawable.ic_b;
            case 'c':
            case 'C':
                return R.drawable.ic_c;
            case 'd':
            case 'D':
                return R.drawable.ic_d;
            case 'e':
            case 'E':
                return R.drawable.ic_e;
            case 'f':
            case 'F':
                return R.drawable.ic_f;
            case 'g':
            case 'G':
                return R.drawable.ic_g;
            case 'h':
            case 'H':
                return R.drawable.ic_h;
            case 'i':
            case 'I':
                return R.drawable.ic_i;
            case 'j':
            case 'J':
                return R.drawable.ic_j;
            case 'k':
            case 'K':
                return R.drawable.ic_k;
            case 'l':
            case 'L':
                return R.drawable.ic_l;
            case 'm':
            case 'M':
                return R.drawable.ic_m;
            case 'n':
            case 'N':
                return R.drawable.ic_n;
            case 'o':
            case 'O':
                return R.drawable.ic_o;
            case 'p':
            case 'P':
                return R.drawable.ic_p;
            case 'q':
            case 'Q':
                return R.drawable.ic_q;
            case 'r':
            case 'R':
                return R.drawable.ic_r;
            case 's':
            case 'S':
                return R.drawable.ic_s;
            case 't':
            case 'T':
                return R.drawable.ic_t;
            case 'u':
            case 'U':
                return R.drawable.ic_u;
            case 'v':
            case 'V':
                return R.drawable.ic_v;
            case 'w':
            case 'W':
                return R.drawable.ic_w;
            case 'x':
            case 'X':
                return R.drawable.ic_x;
            case 'y':
            case 'Y':
                return R.drawable.ic_y;
            case 'z':
            case 'Z':
                return R.drawable.ic_z;
            default:
                return R.drawable.ic_add_white_24dp;
        }
    }
}
