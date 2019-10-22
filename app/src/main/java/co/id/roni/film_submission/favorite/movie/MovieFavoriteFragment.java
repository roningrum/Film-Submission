package co.id.roni.film_submission.favorite.movie;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.favorite.adapter.MovieFavoriteAdapter;
import co.id.roni.film_submission.movies.detail.MovieDetailActivity;


/**
 * A simple {@link Fragment} subclass.
 */

public class MovieFavoriteFragment extends Fragment {
    @BindView(R.id.rv_movies_favs)
    RecyclerView rvMoviesFavs;


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        MovieFavoriteAdapter movieFavoriteAdapter = new MovieFavoriteAdapter(new ArrayList<MovieFavModel>());
        rvMoviesFavs.setAdapter(movieFavoriteAdapter);
        rvMoviesFavs.setLayoutManager(new LinearLayoutManager(getContext()));
        movieFavoriteAdapter.setOnItemClickCallback(movieData -> {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("id", movieData.getId());
            Log.d("Check Intent Id", "Movie Id" + movieData.getId());
            startActivity(intent);
        });

        FavoriteViewModel movieFavModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FavoriteViewModel.class);
        movieFavModel.getMovieLivesData().observe(this, movieFavoriteAdapter::setMovieFavModels);


    }
}
