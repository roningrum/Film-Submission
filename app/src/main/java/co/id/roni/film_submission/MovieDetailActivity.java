package co.id.roni.film_submission;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView imgDetailBackDropMovie;
    private ImageView imgDetailPosterMovie;
    private TextView tvDetailRunTimeMovie;
    private TextView tvDetailTitleMovie;
    private TextView tvDetailOverviewMovie;
    private TextView tvDetailReleaseDateMovie;

    private int id;
    private Observer<MovieDetailModel> getMovieDetail = new Observer<MovieDetailModel>() {
        @Override
        public void onChanged(MovieDetailModel movieDetailModel) {
            if (movieDetailModel != null) {
                showDetailMovie(movieDetailModel);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imgDetailBackDropMovie = findViewById(R.id.img_detail_photo_banner);
        imgDetailPosterMovie = findViewById(R.id.img_movie_poster_detail);
        tvDetailTitleMovie = findViewById(R.id.tv_name_movie_detail);
        tvDetailRunTimeMovie = findViewById(R.id.tv_duration_movie_item);
        tvDetailOverviewMovie = findViewById(R.id.tv_sinopsis_detail);
        tvDetailReleaseDateMovie = findViewById(R.id.tv_release_time_detail);
        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getMovieDetail().observe(this, getMovieDetail);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        movieDetailViewModel.setDetailMovies(id, "en-US");

    }

    @SuppressLint("SetTextI18n")
    private void showDetailMovie(MovieDetailModel movieDetailModel) {
        if (id == movieDetailModel.getId()) {
            if (movieDetailModel.getBackdrop_path() == null) {
                Glide.with(getApplicationContext()).load("http://www.termatools.com/Public/images/nopic.png").into(imgDetailBackDropMovie);
            } else {
                Glide.with(getApplicationContext()).load(movieDetailModel.getBackdrop_path()).into(imgDetailBackDropMovie);
            }

            String duration = String.valueOf(movieDetailModel.getRuntime());
            tvDetailRunTimeMovie.setText(duration + "minute");
            tvDetailTitleMovie.setText(movieDetailModel.getTitle());
            tvDetailOverviewMovie.setText(movieDetailModel.getOverview());
            tvDetailReleaseDateMovie.setText(movieDetailModel.getRelease_date());
            Glide.with(getApplicationContext()).load(movieDetailModel.getPoster_path()).into(imgDetailPosterMovie);

        }
    }
}
