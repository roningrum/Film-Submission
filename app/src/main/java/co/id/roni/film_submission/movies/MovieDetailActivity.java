package co.id.roni.film_submission.movies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.GenreAdapter;
import co.id.roni.film_submission.model.Genre;
import co.id.roni.film_submission.model.MovieDetailModel;

import static co.id.roni.film_submission.R.layout;
import static co.id.roni.film_submission.R.string;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView imgDetailBackDropMovie;
    private ImageView imgDetailPosterMovie;
    private TextView tvDetailRunTimeMovie;
    private TextView tvDetailTitleMovie;
    private TextView tvDetailOverviewMovie;
    private TextView tvDetailReleaseDateMovie;
    private RecyclerView rvGenreList;

    private ProgressBar progressBar;
    private int id;

    private Observer<MovieDetailModel> getMovieDetail = new Observer<MovieDetailModel>() {
        @Override
        public void onChanged(MovieDetailModel movieDetailModel) {
            if (movieDetailModel != null) {
                showDetailMovie(movieDetailModel);
                showLoading(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_movie_detail);
        imgDetailBackDropMovie = findViewById(R.id.img_detail_photo_banner);
        imgDetailPosterMovie = findViewById(R.id.img_movie_poster_detail);
        tvDetailTitleMovie = findViewById(R.id.tv_name_movie_detail);
        tvDetailRunTimeMovie = findViewById(R.id.tv_duration_movie_item);
        tvDetailOverviewMovie = findViewById(R.id.tv_sinopsis_detail);
        tvDetailReleaseDateMovie = findViewById(R.id.tv_release_time_detail);
        rvGenreList = findViewById(R.id.rv_genre_movie_detail);
        progressBar = findViewById(R.id.pb_loading);

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getMovieDetail().observe(this, getMovieDetail);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        movieDetailViewModel.setDetailMovies(id, getString(string.language));
        showLoading(true);

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

            List<Genre> genres = movieDetailModel.getGenres();
            GenreAdapter genreAdapter = new GenreAdapter();
            genreAdapter.setGenreList(genres);

            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            rvGenreList.setLayoutManager(layoutManager);
            rvGenreList.setAdapter(genreAdapter);


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String date = movieDetailModel.getRelease_date();
            try {
                Date newDate = dateFormat.parse(date);
                dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                assert newDate != null;
                tvDetailReleaseDateMovie.setText(dateFormat.format(newDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
