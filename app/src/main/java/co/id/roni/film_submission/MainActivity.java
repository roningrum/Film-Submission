package co.id.roni.film_submission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getListMovies().observe(this, getMovies);

        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        progressBar = findViewById(R.id.pb_loading);

        RecyclerView rvMovies = findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(movieAdapter);

        movieViewModel.setListMovies(1, "en_US");
        showLoading(true);



    }

    private Observer<ArrayList<MovieModel>> getMovies = new Observer<ArrayList<MovieModel>>() {
        @Override
        public void onChanged(ArrayList<MovieModel> movieModels) {
            if (movieModels != null) {
                movieAdapter.setMovieData(movieModels);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
