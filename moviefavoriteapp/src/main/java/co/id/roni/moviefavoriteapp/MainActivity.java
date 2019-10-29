package co.id.roni.moviefavoriteapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String PROVIDER_NAME = "co.id.roni.film_submission";
    private static final String TABLE_NAME = "tbMovieFav";
    private static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    private static final Uri CONTENT_URI = Uri.parse(URL);
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

    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        movieFavoriteAdapter = new MovieFavoriteAdapter(new ArrayList<>());
        rvMovieFavConsumer.setAdapter(movieFavoriteAdapter);
        rvMovieFavConsumer.setLayoutManager(new LinearLayoutManager(this));
        rvMovieFavConsumer.setHasFixedSize(true);

        Cursor cursor = getApplicationContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        MovieFavoriteViewModel movieViewModel = ViewModelProviders.of(this).get(MovieFavoriteViewModel.class);
        movieViewModel.getListMovieFavs().observe(this, getMovieFavs);
        movieViewModel.setListMovieFavs(cursor);


    }

}
