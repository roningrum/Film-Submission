package co.id.roni.film_submission.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import co.id.roni.film_submission.R;
import co.id.roni.film_submission.activity.SettingActivity;

public class DailyNotification extends BroadcastReceiver {
    private static final String REPEATING_REMINDER = "Repeating Alarm";
    private static final int ID_REPEAT_TIME = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Hello, We miss you, Want you Come Back?";
        String title = "Film Submission";
        showAlarmNotification(context, title, message);
    }

    private void showAlarmNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, REPEATING_REMINDER)
                .setSmallIcon(R.drawable.ic_for_movie_widget)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(REPEATING_REMINDER, SettingActivity.REMINDER_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(REPEATING_REMINDER);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
//            notificationManager.notify(ID_REPEAT_TIME);
        }
    }
}
