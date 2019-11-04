package co.id.roni.moviefavoriteapp.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.moviefavoriteapp.FavoriteViewModel;
import co.id.roni.moviefavoriteapp.R;
import co.id.roni.moviefavoriteapp.adapter.MovieFavoriteAdapter;
import co.id.roni.moviefavoriteapp.model.MovieModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static final String PROVIDER_NAME = "co.id.roni.film_submission";
    private static final String TABLE_NAME = "tbMovieFav";
    private static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    private Uri CONTENT_URI = Uri.parse(URL);


    @BindView(R.id.rv_movies_favs)
    RecyclerView rvMovieFavConsumer;
    private MovieFavoriteAdapter movieFavoriteAdapter;


    private Observer<List<MovieModel>> getMovieFavs = new Observer<List<MovieModel>>() {
        @Override
        public void onChanged(List<MovieModel> movieModels) {
            if (movieModels != null) {
                movieFavoriteAdapter.setMovieFavModels(movieModels);
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

        rvMovieFavConsumer.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieFavConsumer.setHasFixedSize(true);
        movieFavoriteAdapter = new MovieFavoriteAdapter(getContext());
        rvMovieFavConsumer.setAdapter(movieFavoriteAdapter);

        FavoriteViewModel movieViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        movieViewModel.getListMovieFavs().observe(this, getMovieFavs);
        Cursor cursorMovie = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursorMovie != null) {
            movieViewModel.setListMovieFavs(cursorMovie);
        }
        movieFavoriteAdapter.notifyDataSetChanged();

    }
}
