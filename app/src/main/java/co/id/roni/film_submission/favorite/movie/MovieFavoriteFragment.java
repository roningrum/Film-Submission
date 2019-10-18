package co.id.roni.film_submission.favorite.movie;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import co.id.roni.film_submission.R;


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
        MovieFavoriteAdapter movieFavoriteAdapter = new MovieFavoriteAdapter();
        rvMoviesFavs.setAdapter(movieFavoriteAdapter);
        rvMoviesFavs.setLayoutManager(new LinearLayoutManager(getContext()));

        FavoriteViewModel movieFavModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        movieFavModel.getMovieLivesData().observe(this, movieFavoriteAdapter::setMovieFavModels);
    }
}
