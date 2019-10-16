package co.id.roni.film_submission;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailTestActivity extends AppCompatActivity {
    ImageView imgTestClipOutline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_test);
        imgTestClipOutline = findViewById(R.id.img_detail_test);

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getWidth(), 16);
            }
        };
        imgTestClipOutline.setClipToOutline(true);
        imgTestClipOutline.setOutlineProvider(viewOutlineProvider);
    }
}
