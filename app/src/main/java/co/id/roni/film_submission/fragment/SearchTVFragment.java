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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.BuildConfig;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.activity.SearchTVActivity;
import co.id.roni.film_submission.activity.TVShowsDetailActivity;
import co.id.roni.film_submission.adapter.TvShowsAdapter;
import co.id.roni.film_submission.model.TVShowModel;
import co.id.roni.film_submission.viewmodel.TVShowsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTVFragment extends Fragment {
    @BindView(R.id.rv_tv_search_result)
    RecyclerView rvTvSearchResult;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoadingSearch;

    private TvShowsAdapter tvShowsAdapter;

    private Observer<List<TVShowModel>> getTvResults = new Observer<List<TVShowModel>>() {
        @Override
        public void onChanged(List<TVShowModel> tvShowModels) {
            if (tvShowModels != null) {
                tvShowsAdapter.setTvData((ArrayList<TVShowModel>) tvShowModels);
                showLoading(false);
            } else {
                showLoading(true);
            }
        }
    };

    public SearchTVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);

        TVShowsViewModel tvShowsViewModel = ViewModelProviders.of(this).get(TVShowsViewModel.class);
        tvShowsViewModel.getListTvs().observe(this, getTvResults);

        tvShowsAdapter = new TvShowsAdapter();
        tvShowsAdapter.notifyDataSetChanged();

        tvShowsAdapter.setOnItemClickCallback(tvShowModel -> {
            Intent searchTvResultIntent = new Intent(getActivity(), TVShowsDetailActivity.class);
            searchTvResultIntent.putExtra("id", tvShowModel.getId());
            startActivity(searchTvResultIntent);
        });

        rvTvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvSearchResult.setAdapter(tvShowsAdapter);

        assert getArguments() != null;
        String queryResult = getArguments().getString(SearchTVActivity.SEARCH_QUERY);

        Log.d("Query Result", " Result " + queryResult);
        tvShowsViewModel.setListSearchTvResult(BuildConfig.API_KEY, queryResult, getResources().getString(R.string.language));


    }

    private void showLoading(Boolean state) {
        if (state) {
            pbLoadingSearch.setVisibility(View.VISIBLE);
        } else {
            pbLoadingSearch.setVisibility(View.GONE);
        }
    }
}
