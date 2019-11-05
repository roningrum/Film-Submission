package co.id.roni.filmsubmission.fragment.favorite;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.filmsubmission.R;
import co.id.roni.filmsubmission.activity.SettingActivity;
import co.id.roni.filmsubmission.adapter.tab.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    @BindView(R.id.tab_layout)
    TabLayout tabFavorite;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar favToolbar;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ViewPagerAdapter favPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        favPagerAdapter.addFragment(new MovieFavoriteFragment(), getString(R.string.movie));
        favPagerAdapter.addFragment(new TVFavoriteFragment(), getString(R.string.tv_series));

        viewPager.setAdapter(favPagerAdapter);
        tabFavorite.setupWithViewPager(viewPager);
        favToolbar.setTitle(R.string.favorite_menu);
        favToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(favToolbar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_setting) {
            Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
            startActivity(settingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
