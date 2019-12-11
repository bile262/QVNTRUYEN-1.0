package com.example.qvntruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.circularreveal.coordinatorlayout.CircularRevealCoordinatorLayout;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    ActionBar actionBar;
    public static ListDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= findViewById(R.id.drawer_layout);
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottm_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlis);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case  R.id.dammy:
                        String tr = "Đam mỹ";
                        Toast.makeText(HomeActivity.this,"Mam mỹ",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, LoaiTruyen.class);

                        intent.putExtra("loaitruyen","Đam mỹ");
                        startActivity(intent);
                        return true;
                    case  R.id.ngontinh:
                        intent = new Intent(HomeActivity.this, LoaiTruyen.class);

                        intent.putExtra("loaitruyen", "Ngôn tình");
                        Toast.makeText(HomeActivity.this,"Ngôn tình",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        return true;
                    case  R.id.tienhiep:
                        intent = new Intent(HomeActivity.this, LoaiTruyen.class);
                        intent.putExtra("loaitruyen", "Tiên hiệp");
                        Toast.makeText(HomeActivity.this,"Tiên hiệp",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        actionBar =getSupportActionBar();

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentHome()).commit();
            bottomNavigationView.setSelectedItemId(R.id.trangchu);
        }
        database= new ListDB(this,"list.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS List(ID INTEGER PRIMARY KEY)");





    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.trangchu:
                actionBar =getSupportActionBar();
                actionBar.setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHome()).commit();
                break;
            case R.id.hot:
                actionBar =getSupportActionBar();
                actionBar.setTitle("Hot");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHot()).commit();
                break;
            case R.id.yeuthich:
                actionBar.setTitle("Yêu thích");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentList()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public  void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu_1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.timkiem:
                Intent intent = new Intent(HomeActivity.this, Search.class);
                startActivity(intent);
                return true;

        }
        return false;
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navlis=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedfragment=null;
                    switch (menuItem.getItemId())
                    {
                        case R.id.trangchu:
                            actionBar.setTitle("Home");
                            selectedfragment= new FragmentHome();
                            break;
                        case R.id.hot:
                            actionBar.setTitle("Mở rộng");
                            selectedfragment= new FragmentHot();
                            break;
                        case R.id.yeuthich:
                            actionBar.setTitle("Yêu thích");
                            selectedfragment= new FragmentList();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment)
                            .commit();
                    return true;
                }
            };
}