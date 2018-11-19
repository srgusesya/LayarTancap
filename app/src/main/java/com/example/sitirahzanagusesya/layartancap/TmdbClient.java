package com.example.sitirahzanagusesya.layartancap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

    public interface TmdbClient{

        @GET("/3/movie/now_playing")
        Call<MovieList> getNowPlayingMovies(@Query("api_key") String api_key);

        @GET("/3/movie/upcoming")
        Call<MovieList> getUpComingMovies(@Query("api_key") String api_key);
    }

