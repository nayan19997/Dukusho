package com.example.dukusho_nv.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import com.example.dukusho_nv.model.Repo;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RepoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LibroListAdapter libroListAdapter;
    String url="https://raw.githubusercontent.com/nayan19997/Dukusho/master/db/repository/repo.json";
    DukushoViewModel dukushoViewModel;

    DatabaseReference mRef;
    String uid;
    String addulr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String repoUrl;
        addulr = getIntent().getStringExtra("ADDREPO");
        if (addulr == null){
            repoUrl = url;
        }else{
            repoUrl = addulr;
        }

        System.out.println("ABCD -> repoURL -> " + repoUrl);

        setContentView(R.layout.activity_repo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mRef = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();

        RecyclerView recyclerView = findViewById(R.id.book_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        libroListAdapter = new LibroListAdapter();
        recyclerView.setAdapter(libroListAdapter);

        dukushoViewModel = ViewModelProviders.of(this).get(DukushoViewModel.class);

        dukushoViewModel.downloadRepo(repoUrl).observe(this, new Observer<Repo>() {
            @Override
            public void onChanged(@Nullable Repo repo) {
                libroListAdapter.bookList = repo.books;
                libroListAdapter.notifyDataSetChanged();
            }
        });

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

    class LibroListAdapter extends RecyclerView.Adapter<LibroListAdapter.BookViewHolder>{

        public List<Book> bookList = new ArrayList<>();

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_viewholder, viewGroup, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final BookViewHolder bookViewHolder, int i) {
            final Book book = bookList.get(i);

            bookViewHolder.title.setText(book.title);

            GlideApp.with(RepoActivity.this)
                    .load(book.portada)
                    .centerCrop()
                    .into(bookViewHolder.portada);

            GlideApp.with(RepoActivity.this)
                    .load(book.tipo)
                    .into(bookViewHolder.tipo);

            bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mRef.child(uid).push().setValue(book);

                    GlideApp.with(RepoActivity.this)
                            .load(book.descargado)
                            .centerCrop()
                            .into(bookViewHolder.portadaDescarga);
                    v.setClickable(false);
                    Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(), "DESCARGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return bookList.size();
        }

        public class BookViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView portada, tipo;
            ImageView portadaDescarga;


            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.book_title);
                portada = itemView.findViewById(R.id.book_portada);
                portadaDescarga = itemView.findViewById(R.id.book_portada_descarga);
                tipo = itemView.findViewById(R.id.tiponovela);


            }
        }
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
        getMenuInflater().inflate(R.menu.repo, menu);
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
            startActivity(new Intent(RepoActivity.this, MyBooksActivity.class));

        } else if (id == R.id.nav_cerrar_sesion) {
            AuthUI.getInstance()
                    .signOut(RepoActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(RepoActivity.this, LogInActivity.class));
                            finish();
                        }
                    });
        } else if (id == R.id.nav_addurl) {
            Intent intent = new Intent(RepoActivity.this, AddRepoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (id == R.id.nav_compartido) {
            startActivity(new Intent(RepoActivity.this, BookSharedActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
