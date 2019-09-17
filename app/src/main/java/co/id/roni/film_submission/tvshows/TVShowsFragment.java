package co.id.roni.film_submission.tvshows;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.id.roni.film_submission.R;
import co.id.roni.film_submission.adapter.TvShowsAdapter;
import co.id.roni.film_submission.model.TvShowModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowsFragment extends Fragment {

    private TvShowsAdapter tvShowsAdapter;
    private ProgressBar progressBar;

    private Observer<List<TvShowModel>> getTvshow = new Observer<List<TvShowModel>>() {
        @Override
        public void onChanged(List<TvShowModel> tvModels) {
            if (tvModels != null) {
                tvShowsAdapter.setMovieData((ArrayList<TvShowModel>) tvModels);
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
        TvShowsViewModel tvViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
        tvViewModel.getListTvs().observe(this, getTvshow);

        tvShowsAdapter = new TvShowsAdapter();
        tvShowsAdapter.notifyDataSetChanged();
        progressBar = view.findViewById(R.id.pb_loading);

        RecyclerView rvTvShows = view.findViewById(R.id.rv_tv_shows);
        rvTvShows.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShows.setAdapter(tvShowsAdapter);

        tvViewModel.setListTVs(1, getString(R.string.language));
        showLoading(true);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
