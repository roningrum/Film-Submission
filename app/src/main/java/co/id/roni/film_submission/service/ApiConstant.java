package co.id.roni.film_submission.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConstant {
    private static Retrofit retrofit = null;
    private Api api = retrofit.create(Api.class);

    public static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
