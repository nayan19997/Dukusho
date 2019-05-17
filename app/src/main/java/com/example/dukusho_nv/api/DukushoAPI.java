package com.example.dukusho_nv.api;

import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.model.Repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DukushoAPI {

    @GET
    Call<Repo>getRepository(@Url String url);

    @GET
    Call<Book>getBook(@Url String url);
}
