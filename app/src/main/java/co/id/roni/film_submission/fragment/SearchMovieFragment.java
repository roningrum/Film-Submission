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
import co.id.roni.film_submission.activity.MovieDetailActivity;
import co.id.roni.film_submission.activity.SearchMovieActivity;
import co.id.roni.film_submission.adapter.MovieAdapter;
import co.id.roni.film_submission.model.MovieModel;
import co.id.roni.film_submission.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment {
    @BindView(R.id.rv_movie_search_result)
    RecyclerView rvMovieSearchResult;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoadingSearch;

    private MovieAdapter movieAdapter;

    private Observer<List<MovieModel>> getMovieResults = new Observer<List<MovieModel>>() {
        @Override
        public void onChanged(List<MovieModel> movieModels) {
            if (movieModels != null) {
                movieAdapter.setMovieData((ArrayList<MovieModel>) movieModels);
                showLoading(false);
            } else {
                showLoading(true);
            }
        }
    };

    public SearchMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getListMovies().observe(this, getMovieResults);

        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();

        movieAdapter.setOnItemClickCallback(movieData -> {
            Intent searchMovieResultIntent = new Intent(getActivity(), MovieDetailActivity.class);
            searchMovieResultIntent.putExtra("id", movieData.getId());
            startActivity(searchMovieResultIntent);
        });

        rvMovieSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieSearchResult.setAdapter(movieAdapter);

        assert getArguments() != null;
        String queryResult = getArguments().getString(SearchMovieActivity.SEARCH_QUERY);
        Log.d("Query Result", " Result " + queryResult);
        movieViewModel.setListSearchMovieResult(BuildConfig.API_KEY, queryResult, getResources().getString(R.string.language));
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbLoadingSearch.setVisibility(View.VISIBLE);
        } else {
            pbLoadingSearch.setVisibility(View.GONE);
        }
    }
}
