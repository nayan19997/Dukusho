package com.example.dukusho_nv.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.dukusho_nv.R;
import com.example.dukusho_nv.view.books.BookPageActivity;
import com.example.dukusho_nv.view.books.GoToBookPageActivity;

import java.io.Serializable;

public class AddRepoActivity extends AppCompatActivity {
    EditText eturl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo);

       eturl= findViewById(R.id.et_url);

        findViewById(R.id.add_repo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO",  eturl.getText().toString());
                startActivity(intent);

            }
        });


    }
}
