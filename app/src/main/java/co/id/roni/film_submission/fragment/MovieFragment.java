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

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.activity.MovieDetailActivity;
import co.id.roni.film_submission.adapter.MovieAdapter;
import co.id.roni.film_submission.model.MovieModel;
import co.id.roni.film_submission.viewmodel.MovieViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    @BindView(R.id.pb_loading)
    ProgressBar progressBar;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    @BindView(R.id.toolbar)
    Toolbar toolbarMovie;

    private MovieAdapter movieAdapter;

    private Observer<List<MovieModel>> getMovies = new Observer<List<MovieModel>>() {
        @Override
        public void onChanged(List<MovieModel> movieModels) {
            if (movieModels != null) {
                movieAdapter.setMovieData((ArrayList<MovieModel>) movieModels);
                showLoading(false);
            }
        }
    };

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getListMovies().observe(this, getMovies);

        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        progressBar = view.findViewById(R.id.pb_loading);

        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(movieData -> {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("id", movieData.getId());
            Log.d("Check Intent Id", "Movie Id" + movieData.getId());
            startActivity(intent);
        });
        movieViewModel.setListMovies(1, getString(R.string.language));
        showLoading(true);
        toolbarMovie.setTitle(R.string.movie);
        toolbarMovie.setTitleTextColor(getResources().getColor(android.R.color.white));

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
