package com.example.dukusho_nv.view.books;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

public class BookInfoActivity extends AppCompatActivity {
    private DukushoViewModel dukushoViewModel;
    private DatabaseReference mRef;
    private String uid;
    ImageView portada, imgpj;
    TextView titlebook,authorname, volumen, pagina,estado,fechadelanzamiento,sinopsis;


    String bookKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        portada = findViewById(R.id.cover);
        imgpj = findViewById(R.id.imgpj);
        titlebook = findViewById(R.id.title_book);
        authorname = findViewById(R.id.author_name);
        volumen = findViewById(R.id.volumen);
        pagina = findViewById(R.id.pagina);
        estado = findViewById(R.id.estado);
        fechadelanzamiento = findViewById(R.id.fechadelanzamiento);
        sinopsis = findViewById(R.id.sinopsis);



//        imageViewfondo =findViewById(R.id.fondomain);

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


        Glide.with(BookInfoActivity.this)
                .load(book.portada)
                .into(portada);
        Glide.with(BookInfoActivity.this)
                .load(book.imgpj)
                .into(imgpj);

        titlebook.setText("Title:"+book.title);
        authorname.setText(book.author);
        volumen.setText(book.volumen);
        pagina.setText(book.pagina);
        estado.setText(book.estado);
        fechadelanzamiento.setText(book.fechadelanzamiento);
        sinopsis.setText(book.sinopsis);




    }


}
