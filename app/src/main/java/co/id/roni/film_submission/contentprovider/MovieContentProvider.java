package co.id.roni.film_submission.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import co.id.roni.film_submission.favorite.FavoriteRepository;
import co.id.roni.film_submission.model.favorite.MovieFavModel;

public class MovieContentProvider extends ContentProvider {

    public static final String AUTHORITY = "co.id.roni.film_submission";
    public static final Uri URI_MOVIEFAVS = Uri.parse("content://" + AUTHORITY + "/" + MovieFavModel.TABLE_NAME);

    public static final int CODE_ALL_MOVIE_FAVS = 1;
    public static final int CODE_ID_MOVIE_FAVS = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, MovieFavModel.TABLE_NAME, CODE_ALL_MOVIE_FAVS);
        MATCHER.addURI(AUTHORITY, MovieFavModel.TABLE_NAME + "/*", CODE_ID_MOVIE_FAVS);
    }

    FavoriteRepository repository;
    MovieFavModel movieFavModel;
    Context context = getContext();
//    MovieDatabase movieDatabase;

    public MovieContentProvider() {
    }

    @Override
    public boolean onCreate() {
        repository = new FavoriteRepository(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (MATCHER.match(URI_MOVIEFAVS)) {
            case CODE_ALL_MOVIE_FAVS:
                cursor = repository.getAllMovieCursor();
                break;
            case CODE_ID_MOVIE_FAVS:
                cursor = null;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + URI_MOVIEFAVS);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if (MATCHER.match(URI_MOVIEFAVS) == CODE_ALL_MOVIE_FAVS) {
            repository.insert(movieFavModel);
        } else {
            return null;
        }
        return Uri.parse(URI_MOVIEFAVS + "/");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (MATCHER.match(URI_MOVIEFAVS) == CODE_ALL_MOVIE_FAVS) {
            repository.delete(movieFavModel.getId());
        }
        throw new IllegalArgumentException("Unknown URI: " + URI_MOVIEFAVS);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


}
