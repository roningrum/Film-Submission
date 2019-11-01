package co.id.roni.film_submission.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import co.id.roni.film_submission.R;
import co.id.roni.film_submission.data.MovieDatabase;
import co.id.roni.film_submission.model.favorite.MovieFavModel;

import static co.id.roni.film_submission.widget.MovieAppBannerWidget.EXTRA_ITEM;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<MovieFavModel> movieFavModelList = new ArrayList<>();
    private MovieDatabase movieDb;

    private Context context;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        movieDb = MovieDatabase.getDatabase(context);
        try {
            movieFavModelList = new GetMovieFavsAsyncTask(movieDb).execute().get();
        } catch (InterruptedException | ExecutionException e) {
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

    private static class GetMovieFavsAsyncTask extends AsyncTask<MovieDatabase, Void, List<MovieFavModel>> {
        MovieDatabase movieDatabase;

        GetMovieFavsAsyncTask(MovieDatabase movieDb) {
            movieDatabase = movieDb;
        }

        @Override
        protected List<MovieFavModel> doInBackground(MovieDatabase... movieDatabases) {
            return movieDatabase.movieDao().getMovieFavsListWidget();
        }
    }
    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return movieFavModelList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        if (movieFavModelList.size() > 0) {
            MovieFavModel movieFavModel = movieFavModelList.get(position);
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
            Intent fillDataIntent = new Intent();
            fillDataIntent.putExtras(extras);
            remoteViews.setOnClickFillInIntent(R.id.image_widget, fillDataIntent);
            return remoteViews;
        } else {
            return null;
        }
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

}
