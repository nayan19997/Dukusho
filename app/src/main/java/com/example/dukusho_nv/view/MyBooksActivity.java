package com.example.dukusho_nv.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.DukushoViewModel;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.example.dukusho_nv.view.books.BookMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyBooksActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LibroListAdapter libroListAdapter;

    DukushoViewModel dukushoViewModel;
    private DatabaseReference mRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books_actvity);
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

        FirebaseRecyclerOptions firebaseOptions = new FirebaseRecyclerOptions.Builder<Book>()
                .setQuery(mRef.child(uid), Book.class)
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_viewholder, viewGroup, false);
            return new BookViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, final int position, @NonNull final Book book) {
            bookViewHolder.title.setText(book.title);
            Glide.with(MyBooksActivity.this).load(book.portada).into(bookViewHolder.portada);

            bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyBooksActivity.this, BookMainActivity.class);
                    intent.putExtra("BOOK_KEY", getRef(position).getKey());
                    startActivity(intent);
                }
            });
        }


        public class BookViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView portada;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.book_title);
                portada = itemView.findViewById(R.id.book_portada);

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
        getMenuInflater().inflate(R.menu.my_books_actvity, menu);
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

        if (id == R.id.nav_repository) {
            startActivity(new Intent(MyBooksActivity.this, RepoActivity.class));

        } else if (id == R.id.nav_addurl) {
            startActivity(new Intent(MyBooksActivity.this, AddRepoActivity.class));


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
