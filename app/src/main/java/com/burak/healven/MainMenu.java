package com.burak.healven;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.burak.healven.helpful.Profile;
import com.burak.healven.helpful.helperFunctions;
import com.burak.healven.main_fragments.AccountFragment;
import com.burak.healven.main_fragments.DiningFragment;
import com.burak.healven.main_fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenu extends AppCompatActivity {


    Profile user;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(toolbarClickListener());

        BottomNavigationView nav = findViewById(R.id.navbar_bottom);
        nav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        swipeRefreshLayout = findViewById(R.id.rl_main_menu);

        swipeRefreshLayout.setOnRefreshListener(helperFunctions.refreshListener(this, swipeRefreshLayout));


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            user = (Profile) bundle.getSerializable("profile");
        }else{
            Log.e("Eror", "Bundle error");
        }


    }

    private View.OnClickListener toolbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, getString(R.string.alreadyBase), Toast.LENGTH_SHORT).show();
            }
        };
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected = null;


            switch(menuItem.getItemId()){
                case R.id.nav_dining:
                    selected = new DiningFragment();
                    break;
                case R.id.nav_account:
                    selected = new AccountFragment(user);
                    break;
                default:
                     selected = new HomeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected).commit();
            return true;
        }
    };




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_actionbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return helperFunctions.menuClickOptions(item.getItemId(), this);
    }


    @Override
    public void onBackPressed() {
        helperFunctions.exitOnBackButton(this);
    }
}
