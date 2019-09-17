package co.id.roni.film_submission;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.id.roni.film_submission.movies.MovieFragment;
import co.id.roni.film_submission.tvshows.TVShowsFragment;

public class MainHomeActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_movie_menu:
                            fragment = new MovieFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                    .commit();
                            return true;
                        case R.id.nav_tvseries_menu:
                            fragment = new TVShowsFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                    .commit();
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        BottomNavigationView navigationView = findViewById(R.id.navigation_menu_home);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        if (savedInstanceState == null) {
            navigationView.setSelectedItemId(R.id.nav_movie_menu);
        }
    }
}
