package com.example.xisko.testme.BroadCast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.xisko.testme.R;

public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    /**
     * Metodo que manega la informacion de la conectividad a internet de nuestro dispositivo
     * @param context
     * @return el tipo de conectividad  que tenemos
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * metodo que convierte a string la informacion de la conectividad de nuestro dispositivo
     * @param context
     * @return
     */
    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = context.getResources().getString(R.string.wifi_on);
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = context.getResources().getString(R.string.data_on);
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = context.getResources().getString(R.string.internet_off);
        }
        return status;
    }
}