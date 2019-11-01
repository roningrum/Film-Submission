package co.id.roni.moviefavoriteapp.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.moviefavoriteapp.FavoriteViewModel;
import co.id.roni.moviefavoriteapp.R;
import co.id.roni.moviefavoriteapp.adapter.TVFavoriteAdapter;
import co.id.roni.moviefavoriteapp.model.TVShowFavModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private static final String PROVIDER_NAME = "co.id.roni.film_submission";
    private static final String TABLE_NAME = "tbTVShowFav";
    private static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    private static final Uri CONTENT_URI = Uri.parse(URL);

    @BindView(R.id.rv_tv_favs)
    RecyclerView rvTvShowFavs;

    private TVFavoriteAdapter tvFavoriteAdapter;
    //
    private Observer<List<TVShowFavModel>> getTvList = new Observer<List<TVShowFavModel>>() {
        @Override
        public void onChanged(List<TVShowFavModel> tvShowFavModels) {
            if (tvShowFavModels != null) {
                tvFavoriteAdapter.setTvShowFavModelList(tvShowFavModels);
            }
        }
    };

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        rvTvShowFavs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShowFavs.setHasFixedSize(true);
        tvFavoriteAdapter = new TVFavoriteAdapter(getContext());
        rvTvShowFavs.setAdapter(tvFavoriteAdapter);

        FavoriteViewModel tvViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        tvViewModel.getListTVFavs().observe(this, getTvList);


        Cursor cursorTV = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursorTV != null) {
            tvViewModel.setListTVFavs(cursorTV);
//        }
        }
    }
}
