package co.id.roni.film_submission;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;

    private Observer<List<MovieModel>> getMovies = new Observer<List<MovieModel>>() {
        @Override
        public void onChanged(List<MovieModel> movieModels) {
            if (movieModels != null) {
                movieAdapter.setMovieData((ArrayList<MovieModel>) movieModels);
                showLoading(false);
            }
        }
    };

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

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieModel movieData) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("EXTRA_MOVIES", movieData);
                intent.putExtra("id", movieData.getId());
                Log.d("Check Intent Id", "Movie Id" + movieData.getId());
                startActivity(intent);
            }
        });

        movieViewModel.setListMovies(1, "en-US");
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
