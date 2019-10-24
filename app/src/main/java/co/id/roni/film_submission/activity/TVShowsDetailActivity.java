package co.id.roni.film_submission.activity;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.BuildConfig;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.CastAdapter;
import co.id.roni.film_submission.adapter.GenreAdapter;
import co.id.roni.film_submission.model.Cast;
import co.id.roni.film_submission.model.Genre;
import co.id.roni.film_submission.model.detailmodel.TVShowDetailModel;
import co.id.roni.film_submission.model.favorite.TVShowFavModel;
import co.id.roni.film_submission.viewmodel.CastDetailViewModel;
import co.id.roni.film_submission.viewmodel.FavoriteViewModel;
import co.id.roni.film_submission.viewmodel.TVShowsDetailViewModel;

public class TVShowsDetailActivity extends AppCompatActivity {
    @BindView(R.id.img_detail_photo_banner)
    ImageView imgDetailBackDropTV;
    @BindView(R.id.img_poster_tv_detail)
    ImageView imgDetailPosterTV;
    @BindView(R.id.tv_name_tv_detail)
    TextView tvDetailTitleTvShow;
    @BindView(R.id.tv_sinopsis_tv_detail)
    TextView tvDetailOverviewTvShow;
    @BindView(R.id.tv_release_time_tv_detail)
    TextView tvReleaseFirstDateTvShow;
    @BindView(R.id.tv_season_tv_detail)
    TextView tvNumberSeasonTvShow;
    @BindView(R.id.rv_genre_tv_detail)
    RecyclerView rvGenreList;
    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

    @BindView(R.id.rv_casts)
    RecyclerView rvCasts;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.view_backdrop_detail)
    View bgBackdrop;

    private int id;
    private String title = "";

    boolean isFavorite = false;
    private LiveData<TVShowFavModel> tvShowFavModelLiveData;
    private Menu menuItem;
    private FavoriteViewModel favoriteViewModel;
    private TVShowsDetailViewModel tvShowsDetailViewModel;


    private Observer<TVShowDetailModel> getTvDetail = tvShowDetailModel -> {
        if (tvShowDetailModel != null) {
            showDetailtvModel(tvShowDetailModel);
            showLoading(false);
        } else {
            showLoading(true);
        }
    };

    private Observer<List<Cast>> getCastTVDetail = castTvDetail -> {
        if (castTvDetail != null) {
            CastAdapter castAdapter = new CastAdapter();
            castAdapter.setCastMovieList(castTvDetail);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);

            rvCasts.setLayoutManager(llm);
            rvCasts.setAdapter(castAdapter);
            rvCasts.setHasFixedSize(true);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_detail);
        ButterKnife.bind(this);

        tvShowsDetailViewModel = ViewModelProviders.of(this).get(TVShowsDetailViewModel.class);
        tvShowsDetailViewModel.getTvShowsDetail().observe(this, getTvDetail);

        CastDetailViewModel castDetailViewModel = ViewModelProviders.of(this).get(CastDetailViewModel.class);
        castDetailViewModel.getCastCreditTvs().observe(this, getCastTVDetail);

        //favorit
        favoriteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(FavoriteViewModel.class);

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
        imgDetailBackDropTV.setClipToOutline(true);
        bgBackdrop.setClipToOutline(true);
        imgDetailBackDropTV.setOutlineProvider(viewOutlineProvider);
        bgBackdrop.setOutlineProvider(viewOutlineProvider);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        setActionBarTitle(title);

        tvShowsDetailViewModel.setDetailTvShows(id, getString(R.string.language));
        String api = BuildConfig.API_KEY;
        castDetailViewModel.setCastCreditTvs(id, api);
        showLoading(true);
        tvShowFavModelLiveData = favoriteViewModel.selectTvFav(id);


        Stetho.initializeWithDefaults(this);
    }

    private void favoriteState() {
        tvShowFavModelLiveData.observe(this, tvShowFavModel -> {
            if (tvShowFavModel != null) {
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

    private void addToFavorite() {
        TVShowDetailModel tvShowDetailModel = tvShowsDetailViewModel.getTvShowsDetail().getValue();
        assert tvShowDetailModel != null;
        int favId = tvShowDetailModel.getId();
        String name = tvShowDetailModel.getName();
        String poster_path = tvShowDetailModel.getPoster_path();
        String overview = tvShowDetailModel.getOverview();
        double vote_overage = tvShowDetailModel.getVote_average();

        TVShowFavModel tvShowFavModel = new TVShowFavModel();
        tvShowFavModel.setId(favId);
        tvShowFavModel.setName(name);
        tvShowFavModel.setPoster_path(poster_path);
        tvShowFavModel.setOverview(overview);
        tvShowFavModel.setVote_average(vote_overage);
        favoriteViewModel.insertTv(tvShowFavModel);
        Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show();

    }

    private void removeFavorite() {
        favoriteViewModel.deleteTv(id);
        Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();


    }
    @SuppressLint("SetTextI18n")
    private void showDetailtvModel(TVShowDetailModel tvShowDetailModel) {
        if (id == tvShowDetailModel.getId()) {
            title = tvShowDetailModel.getName();
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
            tvDetailTitleTvShow.setText(tvShowDetailModel.getName());
            tvDetailOverviewTvShow.setText(tvShowDetailModel.getOverview());
            tvNumberSeasonTvShow.setText(tvShowDetailModel.getNumber_of_seasons() + " " + getString(R.string.season));
            Glide.with(getApplicationContext()).load(tvShowDetailModel.getBackdrop_path()).into(imgDetailBackDropTV);
            Glide.with(getApplicationContext()).load(tvShowDetailModel.getPoster_path()).into(imgDetailPosterTV);

            getGenreList(tvShowDetailModel);
            getFirstReleaseDate(tvShowDetailModel);

        }
    }

    private void getFirstReleaseDate(TVShowDetailModel tvShowDetailModel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = tvShowDetailModel.getFirst_air_date();
        try {
            Date newDate = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            assert newDate != null;
            tvReleaseFirstDateTvShow.setText(dateFormat.format(newDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getGenreList(TVShowDetailModel tvShowDetailModel) {
        List<Genre> genres = tvShowDetailModel.getGenres();
        GenreAdapter genreAdapter = new GenreAdapter();
        genreAdapter.setGenreList(genres);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.BASELINE);
        rvGenreList.setLayoutManager(layoutManager);
        rvGenreList.setAdapter(genreAdapter);
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
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
