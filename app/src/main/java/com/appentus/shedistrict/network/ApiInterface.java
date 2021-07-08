package com.appentus.shedistrict.network;


import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST
    @FormUrlEncoded
    Observable<JsonElement> makeCall(@Url String url, @FieldMap Map<String, String> map);

    @POST()
    Observable<JsonElement> makeCall(@Url String url, @Body RequestBody file);

    @GET()
    Observable<JsonElement> makeCallGet(@Url String url);

    @GET("https://api.unsplash.com/photos")
    @Headers("Authorization:Client-ID dux3XP6NZJm-n9s0MR6jClid6fz-hsdfCllKd9YccyU")
    Observable<JsonElement> unsplashApi(@Query("page") String page);

    @GET("https://api.unsplash.com/search/photos")
    @Headers("Authorization:Client-ID dux3XP6NZJm-n9s0MR6jClid6fz-hsdfCllKd9YccyU")
    Observable<JsonElement> unsplashSearchApi(@Query("page") String page,@Query("query") String query);

    @GET
    Observable<JsonElement> downlload(@Url String fileUrl);
//    Call<ResponseBody> downlload(@Url String fileUrl);

 /*   Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl("http://192.168.43.135/retro/") // REMEMBER TO END with /
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();*/


}
