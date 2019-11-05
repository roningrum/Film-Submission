package co.id.roni.filmsubmission.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import co.id.roni.filmsubmission.favorite.FavoriteRepository;
import co.id.roni.filmsubmission.model.favorite.MovieFavModel;
import co.id.roni.filmsubmission.model.favorite.TVShowFavModel;

public class MovieContentProvider extends ContentProvider {

    public static final String AUTHORITY = "co.id.roni.film_submission";
    public static final Uri URI_MOVIEFAVS = Uri.parse("content://" + AUTHORITY + "/" + MovieFavModel.TABLE_NAME);
    public static final Uri URI_TVFAVS = Uri.parse("content://" + AUTHORITY + "/" + TVShowFavModel.TABLE_NAME);

    public static final int CODE_ALL_MOVIE_FAVS = 1;
    public static final int CODE_ALL_TV_FAVS = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private FavoriteRepository favoriteRepository;

    static {
        MATCHER.addURI(AUTHORITY, MovieFavModel.TABLE_NAME, CODE_ALL_MOVIE_FAVS);
        MATCHER.addURI(AUTHORITY, TVShowFavModel.TABLE_NAME, CODE_ALL_TV_FAVS);
    }


    @Override
    public boolean onCreate() {
        favoriteRepository = new FavoriteRepository(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        int code = MATCHER.match(uri);

        switch (code) {
            case CODE_ALL_MOVIE_FAVS:
                cursor = favoriteRepository.getAllMovieCursor();
                return cursor;
            case CODE_ALL_TV_FAVS:
                cursor = favoriteRepository.getAllTvFavCursor();
                return cursor;
            default:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long id;
        final Context context = getContext();
        if (context == null) {
            return null;
        }
        switch (MATCHER.match(uri)) {
            case CODE_ALL_MOVIE_FAVS:
                id = favoriteRepository.insertMovieFavoriteCursor(MovieFavModel.fromContentValues(values));
                context.getContentResolver().notifyChange(URI_MOVIEFAVS, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_ALL_TV_FAVS:
                id = favoriteRepository.insertTvFavCursor(TVShowFavModel.fromContentValues(values));
                context.getContentResolver().notifyChange(URI_TVFAVS, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


}
