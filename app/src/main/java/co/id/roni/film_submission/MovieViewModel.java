package co.id.roni.film_submission;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = "925c18bd71917db0242931c2fce8c338";
    private MutableLiveData<ArrayList<MovieModel>> listMovies = new MutableLiveData<>();

    void setListMovies( final int page, String language ){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieModel> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language="+language+"&sort_by=popularity.desc&include_adult=false&include_video=false&page="+page;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i< list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieModel movieModel = new MovieModel(movie);
                        listItems.add(movieModel);
                    }
                    listMovies.postValue(listItems);

                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<MovieModel>> getListMovies() {
        return listMovies;
    }
}
