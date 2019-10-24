package co.id.roni.film_submission.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar settingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(settingToolbar);
        settingToolbar.setTitle(R.string.setting);
        settingToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

    }
}
