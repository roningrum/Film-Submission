package co.id.roni.film_submission.movies.detail;

import android.annotation.SuppressLint;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.GenreAdapter;
import co.id.roni.film_submission.favorite.data.MovieDao;
import co.id.roni.film_submission.favorite.data.MovieDatabase;
import co.id.roni.film_submission.favorite.movie.MovieFavModel;
import co.id.roni.film_submission.model.Genre;
import co.id.roni.film_submission.model.detailmodel.MovieDetailModel;

import static co.id.roni.film_submission.R.layout;
import static co.id.roni.film_submission.R.string;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.img_detail_photo_banner)
    ImageView imgDetailBackDropMovie;
    @BindView(R.id.img_movie_poster_detail)
    ImageView imgDetailPosterMovie;
    @BindView(R.id.tv_duration_movie_item)
    TextView tvDetailRunTimeMovie;
    @BindView(R.id.tv_name_movie_detail)
    TextView tvDetailTitleMovie;
    @BindView(R.id.tv_sinopsis_detail)
    TextView tvDetailOverviewMovie;
    @BindView(R.id.tv_release_time_detail)
    TextView tvDetailReleaseDateMovie;
    @BindView(R.id.rv_genre_movie_detail)
    RecyclerView rvGenreList;
    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.view_backdrop_detail)
    View bgBackdrop;


    private int id;
    boolean isFavorite = false;
    Menu menuItem;

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


        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitleEnabled(false);

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, -16, view.getWidth(), view.getHeight(), 16f);
            }
        };
        imgDetailBackDropMovie.setClipToOutline(true);
        bgBackdrop.setClipToOutline(true);
        imgDetailBackDropMovie.setOutlineProvider(viewOutlineProvider);
        bgBackdrop.setOutlineProvider(viewOutlineProvider);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        movieDetailViewModel.setDetailMovies(id, getString(string.language));
        showLoading(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_detail_menu, menu);
        menuItem = menu;
        setFavorite();
        return true;
    }

    private void setFavorite() {
        if (isFavorite) {
            menuItem.getItem(0).setIcon(R.drawable.ic_add_favorite_24dp);
//            isFavorite = true;
        } else {
            menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);
//            isFavorite = false;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_fav) {
            if (isFavorite) {
                removeFavorite();

            } else {
                addToFavorite();

            }
            isFavorite = !isFavorite;
            setFavorite();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void removeFavorite() {
        Toast.makeText(this, "Remove Favorite", Toast.LENGTH_SHORT).show();
    }

    private void addToFavorite() {
        try {
            MovieDao movieDao = MovieDatabase.getDatabase(this.getApplicationContext()).movieDao();
            movieDao.insert(new MovieFavModel());
            Toast.makeText(this, "Add Favorite", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @SuppressLint("SetTextI18n")
    private void showDetailMovie(MovieDetailModel movieDetailModel) {
        if (id == movieDetailModel.getId()) {

            String title = movieDetailModel.getTitle();
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isVisible = true;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        toolbar.setTitle(title);
                        isVisible = true;
                    } else if (isVisible) {
                        toolbar.setTitle("");
                        isVisible = false;
                    }

                }
            });


            String duration = String.valueOf(movieDetailModel.getRuntime());
            tvDetailRunTimeMovie.setText(duration + " " + getString(string.minute));
            tvDetailTitleMovie.setText(movieDetailModel.getTitle());
            tvDetailOverviewMovie.setText(movieDetailModel.getOverview());

            Glide.with(getApplicationContext()).load(movieDetailModel.getBackdrop_path()).into(imgDetailBackDropMovie);
            Glide.with(getApplicationContext()).load(movieDetailModel.getPoster_path()).into(imgDetailPosterMovie);

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
