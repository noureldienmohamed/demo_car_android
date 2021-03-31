package com.softxpert.softxpert.WebService;

import com.softxpert.softxpert.model.CarsListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ApiCallbacks {

    @Headers({"Content-Type:application/json" , "Accept:application/json"})
    @GET()
    Call<CarsListModel> getCarsData(@Url String url);

}