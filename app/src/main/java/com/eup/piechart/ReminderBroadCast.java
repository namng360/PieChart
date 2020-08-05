package com.eup.piechart;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.eup.piechart.activity.Main17Activity;

public class ReminderBroadCast extends BroadcastReceiver {
    int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent myIntent = PendingIntent.getActivity(context, 0, new Intent(context, Main17Activity.class), 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder builder = new Notification.Builder(context, "remind_janki")
                    .setSmallIcon(R.drawable.win)
                    .setContentTitle("Den gio hoc roi")
                    .setContentIntent(myIntent)
                    .setAutoCancel(true)
                    .setContentText("Hihihihihihi" + i++);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(200, builder.build());
        }
    }
}
