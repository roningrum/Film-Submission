package co.id.roni.film_submission.tvshows.detail;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.GenreAdapter;
import co.id.roni.film_submission.model.Genre;
import co.id.roni.film_submission.model.detailmodel.TVShowDetailModel;

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


    private int id;
    private String title = "";


    private Observer<TVShowDetailModel> getTvDetail = tvShowDetailModel -> {
        if (tvShowDetailModel != null) {
            showDetailtvModel(tvShowDetailModel);
            showLoading(false);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_detail);
        ButterKnife.bind(this);

        TVShowsDetailViewModel tvShowsDetailViewModel = ViewModelProviders.of(this).get(TVShowsDetailViewModel.class);
        tvShowsDetailViewModel.getTvShowsDetail().observe(this, getTvDetail);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        setActionBarTitle(title);

        tvShowsDetailViewModel.setDetailTvShows(id, getString(R.string.language));
        showLoading(true);
    }

    @SuppressLint("SetTextI18n")
    private void showDetailtvModel(TVShowDetailModel tvShowDetailModel) {
        if (id == tvShowDetailModel.getId()) {
            title = tvShowDetailModel.getName();
            setActionBarTitle(title);

            String detail = tvShowDetailModel.getName();

            if (detail == null) {
                tvDetailTitleTvShow.setText(R.string.unavailable_overview);
            }
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
