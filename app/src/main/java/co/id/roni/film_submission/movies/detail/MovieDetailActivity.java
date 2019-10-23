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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.BuildConfig;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.CastAdapter;
import co.id.roni.film_submission.adapter.GenreAdapter;
import co.id.roni.film_submission.favorite.movie.FavoriteViewModel;
import co.id.roni.film_submission.favorite.movie.MovieFavModel;
import co.id.roni.film_submission.model.Cast;
import co.id.roni.film_submission.model.Genre;
import co.id.roni.film_submission.model.detailmodel.MovieDetailModel;

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
    @BindView(R.id.rv_casts)
    RecyclerView rvCasts;

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
    Menu menuItem;

    FavoriteViewModel favoriteViewModel;
    MovieDetailViewModel movieDetailViewModel;
    CastDetailViewModel castDetailViewModel;
    MovieDetailModel movieDetailModel;
    CastAdapter adapter;

    LiveData<MovieFavModel> movieFavModelLiveData;
    boolean isFavorite = false;
    String api = BuildConfig.API_KEY;

    private Observer<MovieDetailModel> getMovieDetail = movieDetailModel -> {
        if (movieDetailModel != null) {
            showDetailMovie(movieDetailModel);
            showLoading(false);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getMovieDetail().observe(this, getMovieDetail);

        castDetailViewModel = ViewModelProviders.of(this).get(CastDetailViewModel.class);
        castDetailViewModel.getCastCreditMovies().observe(this, casts -> setCastMovie());

        favoriteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(FavoriteViewModel.class);

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
        castDetailViewModel.setCastCreditMovies(id, api);

        movieFavModelLiveData = favoriteViewModel.selectMovieFav(id);
//        favoriteState();
        showLoading(true);

        Stetho.initializeWithDefaults(this);

    }

    private void setCastMovie() {
        ArrayList<Cast> casts = new ArrayList<>();
        adapter = new CastAdapter();
        adapter.notifyDataSetChanged();
        adapter.setCastMovieList(casts);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvCasts.setLayoutManager(llm);
        rvCasts.setAdapter(adapter);
        rvCasts.setHasFixedSize(true);

    }

    private void favoriteState() {
        movieFavModelLiveData.observe(this, it -> {
            if(it != null) {
                menuItem.getItem(0).setIcon(R.drawable.ic_add_favorite_24dp);
                isFavorite = true;
                invalidateOptionsMenu();
            }
            else{
                menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);
                isFavorite = false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_detail_menu, menu);
        menuItem = menu;
//        setFavorite();
        favoriteState();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_fav) {
            if (isFavorite) {
                removeFavorite();

            } else {
                addToFavorite();

            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void removeFavorite() {
//        if (favoriteViewModel.selectMovieFav(id) != null) {
//            isFavorite = false;
            favoriteViewModel.delete(id);
//            menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);
            Toast.makeText(this, "Remove Favorite", Toast.LENGTH_SHORT).show();
//        }
//        menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);

    }

    private void addToFavorite() {
//        if (favoriteViewModel.selectMovieFav(id) == null) {
            this.movieDetailModel = movieDetailViewModel.getMovieDetail1(id, getString(string.language)).getValue();
            int Favid = movieDetailModel.getId();
            String title = movieDetailModel.getTitle();
            String poster_path = movieDetailModel.getPoster_path();
            String overview = movieDetailModel.getOverview();
            double vote_average = movieDetailModel.getVote_average();

            Log.d("Test Data Insert", "Test : " + title);
            Log.d("Test Data Insert", "Test : " + poster_path);
            Log.d("Test Data Insert", "Test : " + overview);


            MovieFavModel favorite = new MovieFavModel();
            favorite.setId(Favid);
            favorite.setTitle(title);
            favorite.setPoster_path(poster_path);
            favorite.setOverview(overview);
            favorite.setVote_average(vote_average);

//            isFavorite = true;
            favoriteViewModel.insert(favorite);
//            menuItem.getItem(0).setIcon(R.drawable.ic_add_favorite_24dp);
            Toast.makeText(this, "Sukses Favorite", Toast.LENGTH_SHORT).show();
//        }
//        menuItem.getItem(0).setIcon(R.drawable.ic_add_favorite_24dp);
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
