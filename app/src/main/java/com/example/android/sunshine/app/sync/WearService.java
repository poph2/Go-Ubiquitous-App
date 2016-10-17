package com.example.android.sunshine.app.sync;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
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

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(),"Data Changed",Toast.LENGTH_LONG).show();
//            }
//        }).start();


        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(WearService.this.getApplicationContext(),"Data requested from wear...",Toast.LENGTH_SHORT).show();
            }
        });


        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();
                Log.d(TAG, path);
                if (path.equals(WEATHER_PATH)) {

                    DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();

                    SunshineSyncAdapter.syncImmediately(this);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WearService.this.getApplicationContext(),"Data requested from wear...",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }



}
