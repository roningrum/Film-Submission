package co.id.roni.moviefavoriteapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MovieFavoriteFragment movieFavoriteFragment = new MovieFavoriteFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(MovieFavoriteFragment.class.getSimpleName());

        if (!(fragment instanceof MovieFavoriteFragment)) {
            fragmentManager
                    .beginTransaction().
                    add(R.id.container_movie, movieFavoriteFragment, MovieFavoriteFragment.class.getSimpleName())
                    .commit();
        }
    }
}
