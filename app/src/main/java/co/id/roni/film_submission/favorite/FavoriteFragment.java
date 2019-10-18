package co.id.roni.film_submission.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.favorite.adapter.ViewPagerAdapter;
import co.id.roni.film_submission.favorite.movie.MovieFavoriteFragment;


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

    private ViewPagerAdapter favPagerAdapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        favPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        favPagerAdapter.addFragment(new MovieFavoriteFragment(), getString(R.string.movie));
        favPagerAdapter.addFragment(new TVFavoriteFragment(), getString(R.string.tv_series));

        viewPager.setAdapter(favPagerAdapter);
        tabFavorite.setupWithViewPager(viewPager);
    }


}
