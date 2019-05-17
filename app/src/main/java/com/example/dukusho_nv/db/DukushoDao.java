package com.example.dukusho_nv.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dukusho_nv.model.Book;

import java.util.List;

//@Dao
//public interface DukushoDao {
//
//    @Insert
//    void insert(Book book);
//
//    @Query("SELECT * FROM book")
//    LiveData<List<Book>> selectAllBooks();
//
//    @Query("SELECT * FROM book WHERE id = :id")
//    LiveData<Book> selectBook(int id);
//}