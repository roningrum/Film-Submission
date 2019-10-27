package co.id.roni.moviefavoriteapp;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */

public class MovieFavoriteFragment extends Fragment {
    @BindView(R.id.rv_movies_favs)
    RecyclerView rvMovieFavConsumer;

    private static final String PROVIDER_NAME = "co.id.roni.film_submission";
    private static final String URL = "content://" + PROVIDER_NAME;
    private static final Uri CONTENT_URI = Uri.parse(URL);
    private MovieFavoriteAdapter movieFavoriteAdapter;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @SuppressLint("Recycle")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Cursor cursor = Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            ArrayList<MovieModel> favoriteMovieList = new ArrayList<>();
            String title, overview, poster_path;
            double vote_average;
            int id;
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex("id"));
                    poster_path = cursor.getString(cursor.getColumnIndex("poster_path"));
                    title = cursor.getString(cursor.getColumnIndex("title"));
                    overview = cursor.getString(cursor.getColumnIndex("overview"));
                    vote_average = cursor.getDouble(cursor.getColumnIndex("vote_average"));

                    MovieModel movieModel = new MovieModel(id, title, overview, poster_path, vote_average);
                    favoriteMovieList.add(movieModel);
                    cursor.moveToNext();
                }
                while (!cursor.moveToFirst());
            }
            movieFavoriteAdapter.setMovieFavModels(favoriteMovieList);

            movieFavoriteAdapter = new MovieFavoriteAdapter(favoriteMovieList);
            rvMovieFavConsumer.setAdapter(movieFavoriteAdapter);
            rvMovieFavConsumer.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}
