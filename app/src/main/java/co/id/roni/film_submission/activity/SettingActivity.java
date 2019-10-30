package co.id.roni.film_submission.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.notification.DailyNotification;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar settingToolbar;

    @BindView(R.id.tv_language_setting)
    TextView tvSettingLanguage;

    private DailyNotification dailyNotification = new DailyNotification();

    public static CharSequence REMINDER_NAME = "NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(settingToolbar);
        settingToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvSettingLanguage.setOnClickListener(v -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        });

    }
}
