package com.example.dukusho_nv.view.books;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.example.dukusho_nv.model.Page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class BookPageActivity extends AppCompatActivity {
    ImageView fondopage;
    TextView pjname, textopage;
    int seg = 50;
    Button option1, option2, next,previous;

    private String uid;
    private DatabaseReference mRef;
    private  int erroroption;

    Integer pageNum;
    static long size;
    String bookKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        fondopage = findViewById(R.id.fondopage);
        pjname = findViewById(R.id.namepj);
        textopage = findViewById(R.id.textopage);
        next = findViewById(R.id.button_next);
        previous = findViewById(R.id.button_previous);
        option1 = findViewById(R.id.btn_option1);
        option2 = findViewById(R.id.btn_option2);



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


                    loadPage();
                    savePage();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        } else {
            loadPage();
            savePage();
        }
    }


    void savePage(){
        mRef.child(uid).child(bookKey).child("currentPage").setValue(pageNum);
    }

    void loadPage(){

        mRef.child(uid).child(bookKey).child("/pages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println("ABCD -> pnum -> " + pageNum);
        System.out.println("ABCD -> size -> " + size);

        if (size != 0) {
            if (pageNum == size) {
                Toast.makeText(getApplicationContext(), "Libro finish", Toast.LENGTH_LONG).show();
                finish();
            }
        }

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
        if(page == null) return;


        Glide.with(BookPageActivity.this)
                .load(page.image)
                .into(fondopage);
        setSlowlyText(page.text);
        pjname.setText(page.pjname);


//prueba letra por lecta//


        /////






        if (page.option1 != null){

            option1.setText(page.option1.text);
            option2.setText(page.option2.text);

            option1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookPageActivity.this, GoToBookPageActivity.class);
                    intent.putExtra("PAGE_NUM", Integer.valueOf(page.option1.dest));
                    intent.putExtra("BOOK_KEY", bookKey);
                    erroroption = 5;
                    intent.putExtra("ERROROPTION", erroroption);

                    startActivity(intent);

                    finish();
                }
            });

            option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookPageActivity.this, GoToBookPageActivity.class);
                    intent.putExtra("PAGE_NUM", Integer.valueOf(page.option2.dest));
                    intent.putExtra("BOOK_KEY", bookKey);
                    intent.putExtra("ERROROPTION", erroroption);

                    startActivity(intent);
                    finish();
                }
            });

            next.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);

        }else {
            assert option1 != null;
            option1.setVisibility(View.INVISIBLE);
            option2.setVisibility(View.INVISIBLE);

        }

        int prueba = getIntent().getIntExtra("ERROROPTION", 0);

        if (prueba == 5){
            pageNum++;
        }

        if (pageNum == 0){
            previous.setVisibility(View.INVISIBLE);
        }

//
//        button1.setText(page.option1.text);
//        button1.setOnCLickListfs(onclick sta "PAGE_NUM", page.option1.dest)

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookPageActivity.this, GoToBookPageActivity.class);
                intent.putExtra("PAGE_NUM", pageNum +1);
                intent.putExtra("BOOK_KEY", bookKey);
                intent.putExtra("ERROROPTION", erroroption);
                startActivity(intent);
                finish();

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookPageActivity.this, GoToBookPageActivity.class);
                intent.putExtra("PAGE_NUM", pageNum -1);
                intent.putExtra("BOOK_KEY", bookKey);
                intent.putExtra("ERROROPTION", erroroption);

                startActivity(intent);
                finish();

            }
        });


        fondopage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seg = 10;
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.


    private void showSystemUI() {
        int seg = 50;
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    public void setSlowlyText(final String s)
    {


        final TextView tv= (TextView) findViewById(R.id.textopage);
        final int[] i = new int[1];

        i[0] = 0;
        final int length = s.length();
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                char c= s.charAt(i[0]);
                Log.d("Strange",""+c);
                tv.append(String.valueOf(c));
                i[0]++;
            }
        };

        final Timer timer = new Timer();
        TimerTask taskEverySplitSecond = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                if (i[0] == length - 1) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(taskEverySplitSecond, 1, seg);
    }

}
