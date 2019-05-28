package com.example.dukusho_nv;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.model.Repo;

import java.util.List;

public class DukushoViewModel extends AndroidViewModel {
    private DukushoRepository dukushoRepository;

    public DukushoViewModel(@NonNull Application application) {
        super(application);
        dukushoRepository = new DukushoRepository(application);
    }

    public LiveData<Repo> downloadRepo(String url){
        return dukushoRepository.downloadRepo(url);
    }

}