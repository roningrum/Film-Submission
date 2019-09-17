package co.id.roni.film_submission.tvshows;

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

import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.GenreAdapter;
import co.id.roni.film_submission.model.Genre;
import co.id.roni.film_submission.model.TVShowDetailModel;

public class TVShowsDetailActivity extends AppCompatActivity {

    private ImageView imgDetailBackDropTV;
    private ImageView imgDetailPosterTV;
    private TextView tvDetailTitleTvShow;
    private TextView tvDetailOverviewTvShow;
    private TextView tvReleaseFirstDateTvShow;
    private TextView tvNumberSeasonTvShow;
    private RecyclerView rvGenreList;

    private ProgressBar progressBar;
    private int id;


    private Observer<TVShowDetailModel> getTvDetail = new Observer<TVShowDetailModel>() {
        @Override
        public void onChanged(TVShowDetailModel tvShowDetailModel) {
            if (tvShowDetailModel != null) {
                showDetailtvModel(tvShowDetailModel);
                showLoading(false);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_detail);
        tvDetailTitleTvShow = findViewById(R.id.tv_name_tv_detail);
        tvDetailOverviewTvShow = findViewById(R.id.tv_sinopsis_tv_detail);
        imgDetailBackDropTV = findViewById(R.id.img_detail_photo_banner);
        imgDetailPosterTV = findViewById(R.id.img_poster_tv_detail);
        tvReleaseFirstDateTvShow = findViewById(R.id.tv_release_time_tv_detail);
        tvNumberSeasonTvShow = findViewById(R.id.tv_season_tv_detail);

        progressBar = findViewById(R.id.pb_loading);
        rvGenreList = findViewById(R.id.rv_genre_tv_detail);

        TVShowsDetailViewModel tvShowsDetailViewModel = ViewModelProviders.of(this).get(TVShowsDetailViewModel.class);
        tvShowsDetailViewModel.getTvShowsDetail().observe(this, getTvDetail);

        Log.d("Check Id", "Movie Id" + id);
        id = getIntent().getIntExtra("id", id);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        tvShowsDetailViewModel.setDetailTvShows(id, getString(R.string.language));
        showLoading(true);
    }

    @SuppressLint("SetTextI18n")
    private void showDetailtvModel(TVShowDetailModel tvShowDetailModel) {
        if (id == tvShowDetailModel.getId() && getSupportActionBar() != null) {

            getSupportActionBar().setTitle(tvShowDetailModel.getName());
            tvDetailTitleTvShow.setText(tvShowDetailModel.getName());
            tvDetailOverviewTvShow.setText(tvShowDetailModel.getOverview());
            tvNumberSeasonTvShow.setText(tvShowDetailModel.getNumber_of_seasons() + " " + getString(R.string.season));
            Glide.with(getApplicationContext()).load(tvShowDetailModel.getBackdrop_path()).into(imgDetailBackDropTV);
            Glide.with(getApplicationContext()).load(tvShowDetailModel.getPoster_path()).into(imgDetailPosterTV);

            List<Genre> genres = tvShowDetailModel.getGenres();
            GenreAdapter genreAdapter = new GenreAdapter();
            genreAdapter.setGenreList(genres);

            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            layoutManager.setAlignItems(AlignItems.BASELINE);
            rvGenreList.setLayoutManager(layoutManager);
            rvGenreList.setAdapter(genreAdapter);


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
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
