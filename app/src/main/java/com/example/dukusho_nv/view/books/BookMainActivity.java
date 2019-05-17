package com.example.dukusho_nv.view.books;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.DukushoViewModel;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookMainActivity extends AppCompatActivity {

    private DukushoViewModel dukushoViewModel;
    private DatabaseReference mRef;
    private String uid;
    ImageView imageViewfondo;

    String bookKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_main);

        imageViewfondo =findViewById(R.id.fondomain);

        mRef = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();

        bookKey = getIntent().getStringExtra("BOOK_KEY");

        mRef.child(uid).child(bookKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);

                showBook(book);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    void showBook(final Book book){
        findViewById(R.id.button_jugar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookMainActivity.this, BookPageActivity.class);
                intent.putExtra("BOOK_KEY", bookKey);
                startActivity(intent);
                finish();
            }
        });

        Glide.with(BookMainActivity.this)
                .load(book.cover)
                .into(imageViewfondo);

    }
}
