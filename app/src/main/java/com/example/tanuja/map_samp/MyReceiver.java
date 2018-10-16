package com.example.tanuja.map_samp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 1997.tanuja on 11-01-2017.
 */
public class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {


            Intent intent1 = new Intent(context, NotificationService.class);
            context.startService(intent1);
        }
    }
