package com.example.dukusho_nv.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.DukushoViewModel;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.view.books.BookMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyBooksActivity extends AppCompatActivity {
    LibroListAdapter libroListAdapter;

    DukushoViewModel dukushoViewModel;
    private DatabaseReference mRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooks);

        mRef = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();

        RecyclerView recyclerView = findViewById(R.id.book_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions firebaseOptions = new FirebaseRecyclerOptions.Builder<Book>()
                .setQuery(mRef.child(uid), Book.class)
                .setLifecycleOwner(this)
                .build();

        libroListAdapter = new LibroListAdapter(firebaseOptions);
        recyclerView.setAdapter(libroListAdapter);
    }

    class LibroListAdapter extends FirebaseRecyclerAdapter<Book, LibroListAdapter.BookViewHolder> {

        public LibroListAdapter(@NonNull FirebaseRecyclerOptions<Book> options) {
            super(options);
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_viewholder, viewGroup, false);
            return new BookViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, final int position, @NonNull final Book book) {
            bookViewHolder.title.setText(book.title);
            Glide.with(MyBooksActivity.this).load(book.cover).into(bookViewHolder.cover);

            bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyBooksActivity.this, BookMainActivity.class);
                    intent.putExtra("BOOK_KEY", getRef(position).getKey());
                    startActivity(intent);
                }
            });
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

