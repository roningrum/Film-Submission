package co.id.roni.film_submission.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.activity.TVShowsDetailActivity;
import co.id.roni.film_submission.adapter.TvShowsAdapter;
import co.id.roni.film_submission.model.TVShowModel;
import co.id.roni.film_submission.viewmodel.TVShowsViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowsFragment extends Fragment {
    @BindView(R.id.pb_loading)
    ProgressBar progressBar;
    @BindView(R.id.rv_tv_shows)
    RecyclerView rvTvShows;
    @BindView(R.id.toolbar)
    Toolbar tvToolbar;

    @BindString(R.string.language)
    String language;

    private TvShowsAdapter tvShowsAdapter;

    private Observer<List<TVShowModel>> getTvshow = new Observer<List<TVShowModel>>() {
        @Override
        public void onChanged(List<TVShowModel> tvModels) {
            if (tvModels != null) {
                tvShowsAdapter.setMovieData((ArrayList<TVShowModel>) tvModels);
                showLoading(false);
            }
        }
    };

    public TVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        TVShowsViewModel tvViewModel = ViewModelProviders.of(this).get(TVShowsViewModel.class);
        tvViewModel.getListTvs().observe(this, getTvshow);

        tvShowsAdapter = new TvShowsAdapter();
        tvShowsAdapter.notifyDataSetChanged();

        rvTvShows.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShows.setAdapter(tvShowsAdapter);
        tvShowsAdapter.setOnItemClickCallback(tvShowModel -> {
            Intent intent = new Intent(getActivity(), TVShowsDetailActivity.class);
            intent.putExtra("id", tvShowModel.getId());
            Log.d("Check Intent Id", "TV_id" + tvShowModel.getId());
            startActivity(intent);
        });

        tvViewModel.setListTVs(1, language);
        showLoading(true);
        tvToolbar.setTitle(R.string.tv_series);
        tvToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
