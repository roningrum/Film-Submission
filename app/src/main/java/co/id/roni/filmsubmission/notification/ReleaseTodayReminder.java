package co.id.roni.filmsubmission.notification;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.id.roni.filmsubmission.BuildConfig;
import co.id.roni.filmsubmission.R;
import co.id.roni.filmsubmission.activity.MainHomeActivity;
import co.id.roni.filmsubmission.model.MovieModel;
import co.id.roni.filmsubmission.objectdata.MovieObjectData;
import co.id.roni.filmsubmission.service.Api;
import co.id.roni.filmsubmission.service.ApiConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseTodayReminder extends BroadcastReceiver {
    private final static String GROUP_KEY_EMAILS = "group_movie_keys";
    private final static int NOTIFICATION_REQUEST_CODE = 104;
    private final static int MAX_NOTIFICATION = 24;
    public static CharSequence CHANNEL_NAME = "Film Submission";

    private Api api = ApiConstant.getRetrofit().create(Api.class);
    private List<StackNotificationItem> stackNotificationItems = new ArrayList<>();
    private ArrayList<MovieModel> movieModels;
    private int movieId;

    public ReleaseTodayReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovieData(context);
    }

    private void getMovieData(Context context) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(date);
        Call<MovieObjectData> call = api.getMovieRelaase(currentDate, currentDate, BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieObjectData>() {
            @Override
            public void onResponse(Call<MovieObjectData> call, Response<MovieObjectData> response) {

                if (response.isSuccessful()) {
                    movieModels = (ArrayList<MovieModel>) response.body().getResults();
                    for (int i = 0; i < movieModels.size(); i++) {
                        stackNotificationItems.add(new StackNotificationItem(movieModels.get(i).getId(), movieModels.get(i).getTitle(), movieModels.get(i).getOverview()));
                        movieId++;
                    }
                }
                showAlarmNotification(context);
                Log.d("Check Release Data", "Movie :" + movieModels);
            }

            @Override
            public void onFailure(Call<MovieObjectData> call, Throwable t) {
                Log.e("Check Data", "" + t.getMessage());

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
        for (movieId = 1; movieId < movieModels.size(); movieId++) {
            String CHANNLE_ID = "release_movie_chanel";
            if (movieId < MAX_NOTIFICATION) {
                builder = new NotificationCompat.Builder(context, CHANNLE_ID)
                        .setContentTitle(stackNotificationItems.get(movieId).getSender())
                        .setContentText(stackNotificationItems.get(movieId).getMessage())
                        .setSmallIcon(R.drawable.ic_movie)
                        .setLargeIcon(largeIcon)
                        .setGroup(GROUP_KEY_EMAILS)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
            } else {
                int jum = movieModels.size() - 2;
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                        .addLine(stackNotificationItems.get(movieId).getSender() + ":" + stackNotificationItems.get(movieId).getMessage())
                        .addLine(stackNotificationItems.get(movieId - 1).getSender() + ":" + stackNotificationItems.get(movieId).getMessage())
                        .setBigContentTitle(movieId + 1 + context.getString(R.string.new_movie_release) + "")
                        .setSummaryText("+" + jum + context.getString(R.string.more) + "");

                builder = new NotificationCompat.Builder(context, CHANNLE_ID)
                        .setContentTitle(stackNotificationItems.get(movieId).getSender() + "- Release" + setFormatDateNow())
                        .setContentText(stackNotificationItems.get(movieId).getMessage())
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
                notificationManager.notify(movieId, notification);
            }
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
        Toast.makeText(context, context.getString(R.string.repeating_set_alarm), Toast.LENGTH_SHORT).show();

    }

    public void cancelNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);

    }

    private String setFormatDateNow() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(new Date());
    }

}
