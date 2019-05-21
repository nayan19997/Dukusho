package com.example.dukusho_nv.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.view.books.BookPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PreShareActivity extends AppCompatActivity {
    DatabaseReference mRef;
    String uid;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_share);
        mRef = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("Libro");

        TextView tv = findViewById(R.id.titlepreshare);
        tv.setText(book.title);
        ImageView view =  findViewById(R.id.imgpreshare);
        Glide.with(PreShareActivity.this)
                .load(book.portada)
                .into(view);

        final EditText editText = findViewById(R.id.comentpreshare);

        findViewById(R.id.compartir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = editText.getText().toString();
                book.coment = texto;

                mRef.child("/compartido").push().setValue(book);
                Toast.makeText(getApplicationContext(),"Compartido",Toast.LENGTH_SHORT).show();
                finish();


            }
        });
    }
}
