package co.id.roni.moviefavoriteapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.moviefavoriteapp.tab.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabFavoriteConsumer;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewPagerAdapter favPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        favPagerAdapter.addFragment(new MovieFragment(), getString(R.string.movie));
        favPagerAdapter.addFragment(new TvShowFragment(), getString(R.string.tvshow));
        viewPager.setAdapter(favPagerAdapter);
        tabFavoriteConsumer.setupWithViewPager(viewPager);
        toolbar.setTitle(getString(R.string.Favorite));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));




    }

}
