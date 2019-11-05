package co.id.roni.filmsubmission.activity;

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

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.filmsubmission.BuildConfig;
import co.id.roni.filmsubmission.R;
import co.id.roni.filmsubmission.adapter.CastAdapter;
import co.id.roni.filmsubmission.adapter.GenreAdapter;
import co.id.roni.filmsubmission.model.Cast;
import co.id.roni.filmsubmission.model.Genre;
import co.id.roni.filmsubmission.model.detailmodel.MovieDetailModel;
import co.id.roni.filmsubmission.model.favorite.MovieFavModel;
import co.id.roni.filmsubmission.util.DateHelper;
import co.id.roni.filmsubmission.viewmodel.CastDetailViewModel;
import co.id.roni.filmsubmission.viewmodel.FavoriteViewModel;
import co.id.roni.filmsubmission.viewmodel.MovieDetailViewModel;
import co.id.roni.filmsubmission.widget.MovieAppBannerWidget;

import static co.id.roni.filmsubmission.R.string;

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


    boolean isFavorite = false;
    private LiveData<MovieFavModel> movieFavModelLiveData;
    private int id;
    private Menu menuItem;
    private FavoriteViewModel favoriteViewModel;
    private MovieDetailViewModel movieDetailViewModel;

    private CastAdapter adapter;


    private Observer<MovieDetailModel> getMovieDetail = movieDetailModel -> {
        if (movieDetailModel != null) {
            showDetailMovie(movieDetailModel);
            showLoading(false);
        } else {
            showLoading(true);
        }
    };

    private Observer<List<Cast>> getCastMovieDetail = castMovieDetail -> {
        if (castMovieDetail != null) {

            adapter.setCastMovieList(castMovieDetail);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getMovieDetail().observe(this, getMovieDetail);

        CastDetailViewModel castDetailViewModel = ViewModelProviders.of(this).get(CastDetailViewModel.class);
        castDetailViewModel.getCastCreditMovies().observe(this, getCastMovieDetail);

        favoriteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(FavoriteViewModel.class);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
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


        adapter = new CastAdapter();
        rvCasts.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvCasts.setLayoutManager(llm);
        rvCasts.setHasFixedSize(true);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        movieDetailViewModel.setDetailMovies(id, getString(string.language));
        String api = BuildConfig.API_KEY;
        castDetailViewModel.setCastCreditMovies(id, api);

        movieFavModelLiveData = favoriteViewModel.selectMovieFav(id);
        showLoading(true);

        Stetho.initializeWithDefaults(this);

    }

    private void favoriteState() {
        movieFavModelLiveData.observe(this, it -> {
            if (it != null) {
                menuItem.getItem(0).setIcon(R.drawable.ic_add_favorite_24dp);
                isFavorite = true;
                invalidateOptionsMenu();
            } else {
                menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);
                isFavorite = false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_detail_menu, menu);
        menuItem = menu;
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
        favoriteViewModel.delete(id);
        Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
        MovieAppBannerWidget.updateWidget(this);
    }

    private void addToFavorite() {
        MovieDetailModel movieDetailModel = movieDetailViewModel.getMovieDetail1(id, getString(string.language)).getValue();
        assert movieDetailModel != null;
        int Favid = movieDetailModel.getId();
        String title = movieDetailModel.getTitle();
        String poster_path = movieDetailModel.getPoster_path();
        String overview = movieDetailModel.getOverview();
        double vote_average = movieDetailModel.getVote_average();

        Log.d("Test Data Insert", "Test : " + title);
        MovieFavModel favorite = new MovieFavModel();
        favorite.setId(Favid);
        favorite.setTitle(title);
        favorite.setPoster_path(poster_path);
        favorite.setOverview(overview);
        favorite.setVote_average(vote_average);
        favoriteViewModel.insert(favorite);
        Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show();

        MovieAppBannerWidget.updateWidget(this);
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

            String release_date = movieDetailModel.getRelease_date();
            DateHelper datehelper = new DateHelper();
            tvDetailReleaseDateMovie.setText(datehelper.getReleaseDate(release_date));
            getGenreList(movieDetailModel);
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
