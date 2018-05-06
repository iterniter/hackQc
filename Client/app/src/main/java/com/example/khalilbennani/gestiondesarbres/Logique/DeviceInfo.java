package com.example.khalilbennani.gestiondesarbres.Logique;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import java.text.DateFormat;
import java.util.Date;
import android.net.TrafficStats;
import android.util.Log;

/**
 * Created by pcarphin on 2018-04-29.
 */

public class DeviceInfo {

    private String startupBattery = null;
    private String currentBattery = null;
    private String startupTime = null;
    private String currentTime = null;
    private long startupTxBytes = 0;
    private long startupRxBytes = 0;
    private long currentRxBytes = 0;
    private long currentTxBytes = 0;
    private long previousRxBytes = 0;
    private long previousTxBytes = 0;
    private int uid = -1;


    public DeviceInfo(){
        uid = android.os.Process.myUid();
    }

    /**
     * Retourne le niveau de la batterie en chaine de caractère.
     * @param context
     * @return
     */
    public String obtenirBattery(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = context.registerReceiver(null, iFilter);
        if(batteryIntent != null) {
            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if (level > -1 && scale > 0) {
                return Float.toString(((float) level / (float) scale) * 100.0f);
            }
        }

        return null;
    }

    public String getTime(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    public void onStartup(Context context){
        saveStartupTime(context);
        saveStartupBatteryLevel(context);
        startupTxBytes = TrafficStats.getUidTxBytes(uid);
        startupRxBytes = TrafficStats.getUidRxBytes(uid);
    }

    private void saveStartupTime(Context context){
        startupTime = getTime();
    }

    private void saveStartupBatteryLevel(Context context){
        startupBattery = obtenirBattery(context);
    }

    public String getStartupTime(){ return startupTime;}
    public String getStartupBattery(){ return startupBattery;}

    public long getPreviousRxBytes(){
        return previousRxBytes;
    }

    public long getPreviousTxBytes(){
        return previousTxBytes;
    }

    public long getRxBytes(){
        previousRxBytes = currentRxBytes;
        currentRxBytes = TrafficStats.getUidRxBytes(uid) - startupRxBytes;
        Log.i("DeviceInfo", "getRxBytes() called, now currentRxBytes=" + String.valueOf(currentRxBytes) + ", previousRxBytes=" + String.valueOf(previousRxBytes));
        return currentRxBytes;
    }

    public long getTxBytes(){
        previousTxBytes = currentTxBytes;
        currentTxBytes = TrafficStats.getUidTxBytes(uid) - startupTxBytes;
        return currentTxBytes;
    }
}
