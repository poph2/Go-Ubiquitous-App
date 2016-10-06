package com.example.android.sunshine.app.sync;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Pop H2 on 10/1/2016.
 * Pop Inc
 * Lagos Nigeria
 */
public class WearService extends WearableListenerService {

    private static final String TAG = WearService.class.getSimpleName();

    private static final String WEATHER_PATH = "/weather";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        Log.d(TAG, "Data Changed");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"Data Changed",Toast.LENGTH_LONG).show();
            }
        }).start();

        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();
                Log.d(TAG, path);
                if (path.equals(WEATHER_PATH)) {
                    SunshineSyncAdapter.syncImmediately(this);
                }
            }
        }
    }



}
