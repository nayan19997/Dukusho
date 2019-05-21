package com.example.dukusho_nv.view.books;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoToBookPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(GoToBookPageActivity.this, BookPageActivity.class);
        intent.putExtra("PAGE_NUM", getIntent().getIntExtra("PAGE_NUM", 0));
        intent.putExtra("BOOK_KEY", getIntent().getStringExtra("BOOK_KEY"));
        intent.putExtra("ERROROPTION", getIntent().getIntExtra("ERROROPTION", 0));

        startActivity(intent);
        finish();


        int erroroption = getIntent().getIntExtra("ERROROPTION", 0);


    }

}
