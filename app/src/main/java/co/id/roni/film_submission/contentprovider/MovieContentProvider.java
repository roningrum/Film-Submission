package co.id.roni.film_submission.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import co.id.roni.film_submission.favorite.FavoriteRepository;
import co.id.roni.film_submission.model.favorite.MovieFavModel;

public class MovieContentProvider extends ContentProvider {

    public static final String AUTHORITY = "co.id.roni.film_submission";
    public static final Uri URI_MOVIEFAVS = Uri.parse("content://" + AUTHORITY + "/" + MovieFavModel.TABLE_NAME);

    public static final int CODE_ALL_MOVIE_FAVS = 1;
    public static final int CODE_ID_MOVIE_FAVS = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private FavoriteRepository favoriteRepository;

    static {
        MATCHER.addURI(AUTHORITY, MovieFavModel.TABLE_NAME, CODE_ALL_MOVIE_FAVS);
//        MATCHER.addURI(AUTHORITY, MovieFavModel.TABLE_NAME + "/*", CODE_ID_MOVIE_FAVS);
    }


    @Override
    public boolean onCreate() {
        favoriteRepository = new FavoriteRepository(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int code = MATCHER.match(uri);
        if (code == CODE_ALL_MOVIE_FAVS) {
            Cursor cursor;
            cursor = favoriteRepository.getAllMovieCursor();
            Log.d("Check", "Data" + cursor);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + URI_MOVIEFAVS);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        switch (MATCHER.match(uri)) {
            case CODE_ALL_MOVIE_FAVS:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                long id = favoriteRepository.insertMovieFavoriteCursor(MovieFavModel.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_ID_MOVIE_FAVS:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
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
