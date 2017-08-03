package sqlitedemo.com.sqlitedemo;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by JAWED on 03-07-2017.
 */

public class Message {


    public static void message(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
