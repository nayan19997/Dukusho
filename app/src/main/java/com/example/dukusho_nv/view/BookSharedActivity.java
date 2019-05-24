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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dukusho_nv.DukushoViewModel;
import com.example.dukusho_nv.GlideApp;
import com.example.dukusho_nv.LogInActivity;
import com.example.dukusho_nv.R;
import com.example.dukusho_nv.model.Book;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookSharedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    LibroListAdapter libroListAdapter;

    DukushoViewModel dukushoViewModel;
    private DatabaseReference mRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_sharede);
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        FirebaseRecyclerOptions firebaseOptions = new FirebaseRecyclerOptions.Builder<Book>()
                .setQuery(mRef.child("/compartido"), Book.class)
                .setLifecycleOwner(this)
                .build();

        libroListAdapter = new LibroListAdapter(firebaseOptions);
        recyclerView.setAdapter(libroListAdapter);

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



    class LibroListAdapter extends FirebaseRecyclerAdapter<Book, LibroListAdapter.BookViewHolder> {

        public LibroListAdapter(@NonNull FirebaseRecyclerOptions<Book> options) {
            super(options);
        }



        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookshared_item, viewGroup, false);
            return new BookViewHolder(view);

        }

        @Override
        protected void  onBindViewHolder(@NonNull final BookViewHolder bookViewHolder , final int position, @NonNull final Book book) {


            GlideApp.with(BookSharedActivity.this).load(book.userimg).circleCrop().into(bookViewHolder.userimg);
            bookViewHolder.username.setText(book.username);

            bookViewHolder.title.setText(book.title);
            Glide.with(BookSharedActivity.this).load(book.portada).into(bookViewHolder.portada);
            bookViewHolder.authorname.setText(book.author);
            bookViewHolder.coment.setText(book.coment);

            bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mRef.child(uid).push().setValue(book);

                    Glide.with(BookSharedActivity.this).load(book.descargado).into(bookViewHolder.portada);
                    v.setClickable(false);
                    Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(), "DESCARGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(BookSharedActivity.this, MyBooksActivity.class));

                }
            });

        }


        public class BookViewHolder extends RecyclerView.ViewHolder {
            TextView username,title,authorname,coment;
            ImageView userimg,portada;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                username= itemView.findViewById(R.id.user_name);
                userimg = itemView.findViewById(R.id.user_img);

                portada = itemView.findViewById(R.id.book_img);
                title = itemView.findViewById(R.id.book_title);
                authorname = itemView.findViewById(R.id.book_author);
                coment = itemView.findViewById(R.id.book_coment);

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
        getMenuInflater().inflate(R.menu.books_sharede, menu);
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
            startActivity(new Intent(BookSharedActivity.this, MyBooksActivity.class));

        } else if (id == R.id.nav_cerrar_sesion) {
            AuthUI.getInstance()
                    .signOut(BookSharedActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(BookSharedActivity.this, LogInActivity.class));
                            finish();
                        }
                    });
        } else if (id == R.id.nav_addurl) {
            startActivity(new Intent(BookSharedActivity.this, AddRepoActivity.class));
        }else if (id == R.id.nav_compartido) {
            startActivity(new Intent(BookSharedActivity.this, BookSharedActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
