package co.id.roni.film_submission.widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.ExecutionException;

import co.id.roni.film_submission.R;
import co.id.roni.film_submission.data.MovieDatabase;
import co.id.roni.film_submission.model.favorite.MovieFavModel;
import co.id.roni.film_submission.viewmodel.FavoriteViewModel;

import static co.id.roni.film_submission.widget.MovieAppBannerWidget.EXTRA_ITEM;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private LiveData<List<MovieFavModel>> movieFavModelList;
    private MovieDatabase movieDb;

    private Context context;
    private Application application;

    StackRemoteViewsFactory(Context context, Application application) {
        this.context = context;
        this.application = application;
    }

    @Override
    public void onCreate() {
        movieDb = MovieDatabase.getDatabase(context);
        try {
            movieFavModelList = new GetMovieFavsAsyncTask(movieDb).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDataSetChanged() {
        try {
            movieFavModelList = new GetMovieFavsAsyncTask(movieDb).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieFavModelList.getValue().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        FavoriteViewModel favoriteViewModel = new ViewModelProvider((ViewModelStoreOwner) this, new ViewModelProvider.AndroidViewModelFactory(application)).get(FavoriteViewModel.class);
        favoriteViewModel.getMovieLivesData().observe((LifecycleOwner) context, movieFavModels -> {
            if (movieFavModels.size() > 0) {
                MovieFavModel movieFavModel = movieFavModels.get(position);
                try {
                    Bitmap bitmap = Glide.with(context)
                            .asBitmap()
                            .load(movieFavModel.getPoster_path())
                            .submit(512, 512)
                            .get();
                    remoteViews.setImageViewBitmap(R.id.image_widget, bitmap);

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                Bundle extras = new Bundle();
                extras.putString(EXTRA_ITEM, movieFavModel.getTitle());
                Intent fillIntent = new Intent();
                remoteViews.setOnClickFillInIntent(R.id.image_widget, fillIntent);
            } else {
                Log.d("ITEM", "Null");
            }
        });
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private static class GetMovieFavsAsyncTask extends AsyncTask<MovieDatabase, Void, LiveData<List<MovieFavModel>>> {
        MovieDatabase movieDatabase;

        GetMovieFavsAsyncTask(MovieDatabase movieDb) {
            movieDatabase = movieDb;
        }

        @Override
        protected LiveData<List<MovieFavModel>> doInBackground(MovieDatabase... movieDatabases) {
            return movieDatabase.movieDao().getAllMovieFavs();
        }
    }
}
