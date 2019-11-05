package co.id.roni.filmsubmission.fragment.favorite;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.filmsubmission.R;
import co.id.roni.filmsubmission.activity.TVShowsDetailActivity;
import co.id.roni.filmsubmission.adapter.favorite.TVFavoriteAdapter;
import co.id.roni.filmsubmission.viewmodel.FavoriteViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVFavoriteFragment extends Fragment {
    @BindView(R.id.rv_tv_favs)
    RecyclerView rvTvFavs;


    public TVFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvfavorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        TVFavoriteAdapter tvFavoriteAdapter = new TVFavoriteAdapter(new ArrayList<>());
        rvTvFavs.setAdapter(tvFavoriteAdapter);
        rvTvFavs.setLayoutManager(new LinearLayoutManager(getContext()));
        tvFavoriteAdapter.setOnItemClickCallback(tvShowFavModel -> {
            Intent intent = new Intent(getActivity(), TVShowsDetailActivity.class);
            intent.putExtra("id", tvShowFavModel.getId());
            Log.d("Check Intent Id", "TV Id" + tvShowFavModel.getId());
            startActivity(intent);
        });
        FavoriteViewModel tvFavViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FavoriteViewModel.class);
        tvFavViewModel.getTvLivesData().observe(this, tvFavoriteAdapter::setTvShowFavModelList);
    }
}
