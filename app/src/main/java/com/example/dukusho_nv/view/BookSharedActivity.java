package com.example.dukusho_nv.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.DukushoViewModel;
import com.example.dukusho_nv.GlideApp;
import com.example.dukusho_nv.LogInActivity;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.view.books.BookMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookSharedActivity extends AppCompatActivity {

    LibroListAdapter libroListAdapter;

    DukushoViewModel dukushoViewModel;
    private DatabaseReference mRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shared);




        mRef = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();

        RecyclerView recyclerView = findViewById(R.id.book_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        FirebaseRecyclerOptions firebaseOptions = new FirebaseRecyclerOptions.Builder<Book>()
                .setQuery(mRef.child("/compartido"), Book.class)
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookshared_item, viewGroup, false);
            return new BookViewHolder(view);

        }

        @Override
        protected void  onBindViewHolder(@NonNull final BookViewHolder bookViewHolder , final int position, @NonNull final Book book) {


            GlideApp.with(BookSharedActivity.this).load(book.userimg).circleCrop().into(bookViewHolder.userimg);
            bookViewHolder.username.setText(book.username);

            bookViewHolder.title.setText(book.title);
            Glide.with(BookSharedActivity.this).load(book.portada).into(bookViewHolder.portada);
            bookViewHolder.authorname.setText(book.author);
            bookViewHolder.coment.setText(book.coment);

            bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mRef.child(uid).push().setValue(book);

                    Glide.with(BookSharedActivity.this).load(book.descargado).into(bookViewHolder.portada);
                    v.setClickable(false);
                    Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(), "DESCARGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }
            });

        }


        public class BookViewHolder extends RecyclerView.ViewHolder {
            TextView username,title,authorname,coment;
            ImageView userimg,portada;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                username= itemView.findViewById(R.id.user_name);
                userimg = itemView.findViewById(R.id.user_img);

                portada = itemView.findViewById(R.id.book_img);
                title = itemView.findViewById(R.id.book_title);
                authorname = itemView.findViewById(R.id.book_author);
                coment = itemView.findViewById(R.id.book_coment);

            }
        }


    }
}
