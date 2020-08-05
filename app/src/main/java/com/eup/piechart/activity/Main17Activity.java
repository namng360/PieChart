package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eup.piechart.R;
import com.eup.piechart.ReminderBroadCast;

import java.util.Calendar;

public class Main17Activity extends AppCompatActivity {
    Button start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main17);
        createNotificationChannel();
        start= findViewById(R.id.button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Main17Activity.this, "Reminder set!", Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 17);
                calendar.set(Calendar.MINUTE, 14);
                calendar.set(Calendar.SECOND, 0);

//                Intent intent = new Intent(getApplicationContext(), ReminderBroadCast.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100,
//                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                Intent intent = new Intent(Main17Activity.this, ReminderBroadCast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Main17Activity.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                long timeAtButtonClick = System.currentTimeMillis();
//                long tenSecondsInMilis = 1000 * 10;
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick,
//                        timeAtButtonClick + tenSecondsInMilis, pendingIntent);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        });
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "RemindJanki";
            String description = "Channel for Janki remind";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("remind_janki", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


}
