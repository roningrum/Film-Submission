package co.id.roni.film_submission.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.favorite.FavoriteFragment;
import co.id.roni.film_submission.movies.MovieFragment;
import co.id.roni.film_submission.tvshows.TVShowsFragment;

public class MainHomeActivity extends AppCompatActivity {
    @BindView(R.id.navigation_menu_home)
    BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            menuItem -> {
                Fragment fragment;

                switch (menuItem.getItemId()) {
                    case R.id.nav_movie_menu:
                        String title = getString(R.string.movie);
                        setActionBarTitle(title);
                        fragment = new MovieFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.nav_tvseries_menu:
                        title = getString(R.string.tv_series);
                        setActionBarTitle(title);
                        fragment = new TVShowsFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.nav_favorite_menu:
                        title = getString(R.string.favorite_menu);
                        setActionBarTitle(title);
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

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_languange) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
