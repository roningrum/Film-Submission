package co.id.roni.film_submission.movies.detail;

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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.GenreAdapter;
import co.id.roni.film_submission.model.Genre;
import co.id.roni.film_submission.model.detailmodel.MovieDetailModel;

import static co.id.roni.film_submission.R.layout;
import static co.id.roni.film_submission.R.string;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.img_detail_photo_banner)
    ImageView imgDetailBackDropMovie;
    @BindView(R.id.img_movie_poster_detail)
    ImageView imgDetailPosterMovie;
    @BindView(R.id.tv_name_movie_detail)
    TextView tvDetailRunTimeMovie;
    @BindView(R.id.tv_duration_movie_item)
    TextView tvDetailTitleMovie;
    @BindView(R.id.tv_sinopsis_detail)
    TextView tvDetailOverviewMovie;
    @BindView(R.id.tv_release_time_detail)
    TextView tvDetailReleaseDateMovie;
    @BindView(R.id.rv_genre_movie_detail)
    RecyclerView rvGenreList;
    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

    private int id;

    private Observer<MovieDetailModel> getMovieDetail = movieDetailModel -> {
        if (movieDetailModel != null) {
            showDetailMovie(movieDetailModel);
            showLoading(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_movie_detail);

        ButterKnife.bind(this);

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getMovieDetail().observe(this, getMovieDetail);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        movieDetailViewModel.setDetailMovies(id, getString(string.language));
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        showLoading(true);

    }

    @SuppressLint("SetTextI18n")
    private void showDetailMovie(MovieDetailModel movieDetailModel) {
        if (id == movieDetailModel.getId() && getSupportActionBar() != null) {

            getSupportActionBar().setTitle(movieDetailModel.getTitle());

            String duration = String.valueOf(movieDetailModel.getRuntime());
            tvDetailRunTimeMovie.setText(duration + " " + getString(R.string.minute));
            tvDetailTitleMovie.setText(movieDetailModel.getTitle());
            tvDetailOverviewMovie.setText(movieDetailModel.getOverview());
            Glide.with(getApplicationContext()).load(movieDetailModel.getPoster_path()).into(imgDetailPosterMovie);
            Glide.with(getApplicationContext()).load(movieDetailModel.getBackdrop_path()).into(imgDetailBackDropMovie);

            getGenreList(movieDetailModel);
            getReleaseDate(movieDetailModel);


        }
    }

    private void getReleaseDate(MovieDetailModel movieDetailModel) {
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

    private void getGenreList(MovieDetailModel movieDetailModel) {
        List<Genre> genres = movieDetailModel.getGenres();
        GenreAdapter genreAdapter = new GenreAdapter();
        genreAdapter.setGenreList(genres);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.BASELINE);
        rvGenreList.setLayoutManager(layoutManager);
        rvGenreList.setAdapter(genreAdapter);

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
