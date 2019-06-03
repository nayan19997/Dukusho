package com.example.dukusho_nv.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dukusho_nv.GlideApp;
import com.example.dukusho_nv.LogInActivity;
import com.example.dukusho_nv.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AddRepoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText eturl;
    String repository1, repository2, repository3, repository4, repository5,repository6,repository7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repoo);

        repository1="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/reposclawhammer.json";
        repository2="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/repokurohaneko.json";
        repository3="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/repoalien.json";
        repository4="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/repocomic.json";
        repository5="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/repoprincess.json";
        repository6="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/repodiabolic.json";
        repository7="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/repo1.json";

        eturl= findViewById(R.id.et_url);

        findViewById(R.id.add_repo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO",  eturl.getText().toString());
                startActivity(intent);
            }
        });



        findViewById(R.id.repo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO",  repository1);
                startActivity(intent);
            }
        });


        findViewById(R.id.repo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO", repository2);
                startActivity(intent);
            }
        });

        findViewById(R.id.repo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO", repository3);
                startActivity(intent);
            }
        });

        findViewById(R.id.repo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO",  repository4);
                startActivity(intent);
            }
        });

        findViewById(R.id.repo5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO",  repository5);
                startActivity(intent);
            }
        });

        findViewById(R.id.repo6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO", repository6);
                startActivity(intent);
            }
        });

        findViewById(R.id.repo7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRepoActivity.this, RepoActivity.class);
                intent.putExtra("ADDREPO",  repository7);
                startActivity(intent);
            }
        });









        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);






        navigationView.setNavigationItemSelectedListener(this);

        /* Load user info in drawer header*/
        View header = navigationView.getHeaderView(0);
        ImageView photo = header.findViewById(R.id.userPhoto);
        TextView name = header.findViewById(R.id.userName);
        TextView email = header.findViewById(R.id.userEmail);

        GlideApp.with(this)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .circleCrop()
                .into(photo);
        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_repoo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mybooks) {
            startActivity(new Intent(AddRepoActivity.this, MyBooksActivity.class));

        } else if (id == R.id.nav_cerrar_sesion) {
            AuthUI.getInstance()
                    .signOut(AddRepoActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(AddRepoActivity.this, LogInActivity.class));
                            finish();
                        }
                    });
        } else if (id == R.id.nav_addurl) {
            startActivity(new Intent(AddRepoActivity.this, AddRepoActivity.class));
        }else if (id == R.id.nav_compartido) {
            startActivity(new Intent(AddRepoActivity.this, BookSharedActivity.class));
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
