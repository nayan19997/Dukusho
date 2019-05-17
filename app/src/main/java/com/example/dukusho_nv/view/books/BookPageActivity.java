package com.example.dukusho_nv.view.books;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.model.Page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookPageActivity extends AppCompatActivity {
    ImageView fondopage;
    TextView namepj, textopage;
    Button option1, option2;

    private String uid;
    private DatabaseReference mRef;

    Integer pageNum;
    String bookKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        fondopage = findViewById(R.id.fondopage);
        namepj = findViewById(R.id.namepj);
        textopage = findViewById(R.id.textopage);
//        option1 = findViewById(R.id.option1);

        mRef = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();


        bookKey = getIntent().getStringExtra("BOOK_KEY");
        pageNum = getIntent().getIntExtra("PAGE_NUM", -1);

        if(pageNum == -1) { // venimos de mybooks
            mRef.child(uid).child(bookKey).child("currentPage").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    pageNum = dataSnapshot.getValue(Integer.class);

                    if(pageNum == null){
                        pageNum = 0;
                    }

                    savePage();
                    loadPage();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        } else {
            savePage();
            loadPage();
        }
    }


    void savePage(){
        mRef.child(uid).child(bookKey).child("currentPage").setValue(pageNum);
    }

    void loadPage(){
        mRef.child(uid).child(bookKey).child("/pages").child(String.valueOf(pageNum)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {

                Page page = dataSnapshot.getValue(Page.class);
                showPage(page);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    void showPage(final Page page) {
        Log.e("abc", page.text);

        Glide.with(BookPageActivity.this)
                .load(page.image)
                .into(fondopage);

//
//        button1.setText(page.option1.text);
//        button1.setOnCLickListfs(onclick sta "PAGE_NUM", page.option1.dest)

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookPageActivity.this, BookPageActivity.class);
                intent.putExtra("PAGE_NUM", pageNum + 1);
                startActivity(intent);
                finish();

            }
        });
//        namepj.setText(page.);
    }

}
