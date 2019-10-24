package co.id.roni.film_submission.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.fragment.MovieFragment;
import co.id.roni.film_submission.fragment.TVShowsFragment;
import co.id.roni.film_submission.fragment.favorite.FavoriteFragment;

public class MainHomeActivity extends AppCompatActivity {
    @BindView(R.id.navigation_menu_home)
    BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            menuItem -> {
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
                    case R.id.nav_favorite_menu:
                        fragment = new FavoriteFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ButterKnife.bind(this);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navigationView.setSelectedItemId(R.id.nav_movie_menu);
        }
    }
}
