package co.id.roni.film_submission.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.id.roni.film_submission.BuildConfig;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.activity.MainHomeActivity;
import co.id.roni.film_submission.model.MovieModel;
import co.id.roni.film_submission.objectdata.MovieObjectData;
import co.id.roni.film_submission.service.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReleaseTodayReminder extends BroadcastReceiver {
    private final static String GROUP_KEY_EMAILS = "group_movie_keys";
    private final static int NOTIFICATION_REQUEST_CODE = 104;
    private final static int MAX_NOTIFICATION = 2;
    public static CharSequence CHANNEL_NAME = "Film Submission";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private Api api = retrofit.create(Api.class);
    private List<StackNotificationItem> stackNotificationItems = new ArrayList<>();
    private ArrayList<MovieModel> movieModels;
    private int notifCount = 0;

    public ReleaseTodayReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovieData(context);
    }

    private void getMovieData(Context context) {
        final Locale current = context.getResources().getConfiguration().locale;
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(date);
        Call<MovieObjectData> call = api.getMovieRelaase(currentDate, currentDate, BuildConfig.API_KEY, current.getLanguage());
        call.enqueue(new Callback<MovieObjectData>() {
            @Override
            public void onResponse(Call<MovieObjectData> call, Response<MovieObjectData> response) {

                movieModels = (ArrayList<MovieModel>) response.body().getResults();
                for (int i = 0; i < movieModels.size(); i++) {
                    stackNotificationItems.add(new StackNotificationItem(movieModels.get(i).getId(), movieModels.get(i).getTitle(), movieModels.get(i).getOverview()));
                    notifCount++;
                }
                showAlarmNotification(context);
                Log.d("Check Release Data", "Movie :" + movieModels);
            }

            @Override
            public void onFailure(Call<MovieObjectData> call, Throwable t) {
                Log.d("Check Data", "" + t.getMessage());

            }
        });
    }

    private void showAlarmNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie);
        Intent intent = new Intent(context, MainHomeActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;


        String CHANNLE_ID = "release_movie_chanel";
        if (notifCount < MAX_NOTIFICATION) {
            builder = new NotificationCompat.Builder(context, CHANNLE_ID)
                    .setContentTitle(stackNotificationItems.get(notifCount).getSender())
                    .setContentText(stackNotificationItems.get(notifCount).getMessage())
                    .setSmallIcon(R.drawable.ic_movie)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .setBigContentTitle(notifCount + "new movie release today")
                    .setSummaryText("Movie Release Reminder");
            for (int i = 0; i < movieModels.size(); i++) {
                inboxStyle.addLine(stackNotificationItems.get(notifCount).getSender() + ":" + stackNotificationItems.get(notifCount).getMessage());
            }
            builder = new NotificationCompat.Builder(context, CHANNLE_ID)
                    .setContentTitle(notifCount + "new movie relase today")
                    .setContentText("Movie Release Reminder")
                    .setSmallIcon(R.drawable.ic_movie)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNLE_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNLE_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(notifCount, notification);
        }
    }

    public void setRepeatingReminder(Context context) {
        cancelNotification(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Repeating Alarm Set Up", Toast.LENGTH_SHORT).show();

    }

    public void cancelNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);

    }

}
