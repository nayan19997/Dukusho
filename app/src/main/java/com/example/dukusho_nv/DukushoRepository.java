package com.example.dukusho_nv;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.model.Repo;
import com.example.dukusho_nv.api.DukushoAPI;
import com.example.dukusho_nv.api.DukushoApiModule;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DukushoRepository {
    DukushoAPI dukushoAPI;

    public DukushoRepository(Application application){
        dukushoAPI = DukushoApiModule.getAPI();
    }

    public LiveData<Repo> downloadRepo(String url){
        final MutableLiveData<Repo> repo = new MutableLiveData<>();

        dukushoAPI.getRepository(url).enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                repo.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
            }
        });

        return repo;
    }


}
