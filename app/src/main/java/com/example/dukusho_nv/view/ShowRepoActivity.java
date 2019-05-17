package com.example.dukusho_nv.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.DukushoViewModel;
import com.example.dukusho_nv.LogInActivity;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.model.Repo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowRepoActivity extends AppCompatActivity {
    LibroListAdapter libroListAdapter;
    final String repoUrl = "https://raw.githubusercontent.com/nayan19997/Dukusho_NV/master/db/repository/repo.json";
    DukushoViewModel dukushoViewModel;

    DatabaseReference mRef;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrepo);

        mRef = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();

        RecyclerView recyclerView = findViewById(R.id.book_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        libroListAdapter = new LibroListAdapter();
        recyclerView.setAdapter(libroListAdapter);

        dukushoViewModel = ViewModelProviders.of(this).get(DukushoViewModel.class);

        dukushoViewModel.downloadRepo(repoUrl).observe(this, new Observer<Repo>() {
            @Override
            public void onChanged(@Nullable Repo repo) {
                libroListAdapter.bookList = repo.books;
                libroListAdapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.btnmybooks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowRepoActivity.this, MyBooksActivity.class));

            }
        });

    }

    class LibroListAdapter extends RecyclerView.Adapter<LibroListAdapter.BookViewHolder>{

        public List<Book> bookList = new ArrayList<>();

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_viewholder, viewGroup, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
            final Book book = bookList.get(i);

            bookViewHolder.title.setText(book.title);
            Glide.with(ShowRepoActivity.this).load(book.cover).into(bookViewHolder.cover);

            bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRef.child(uid).push().setValue(book);
//                    mRef.child(uid).child(book.url).setValue(book);
                }
            });
        }

        @Override
        public int getItemCount() {
            return bookList.size();
        }

        public class BookViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView cover;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.book_title);
                cover = itemView.findViewById(R.id.book_cover);

            }
        }
    }


}

// $userId$
//       http://gitt.libr1.json: 0
//       http://gitt.libr2.json: 3
//       http://gitt.libr3.json: 7