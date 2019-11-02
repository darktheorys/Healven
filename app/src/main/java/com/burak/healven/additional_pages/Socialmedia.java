package com.burak.healven.additional_pages;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.burak.healven.R;
import com.burak.healven.helpful.RecyclerViewAdapterForSM;
import com.burak.healven.helpful.helperFunctions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Socialmedia extends AppCompatActivity {

    private ArrayList<Integer> imagePaths= new ArrayList<>();
    private ArrayList<String> sm_names = new ArrayList<>();
    private ArrayList<String> sm_names2 = new ArrayList<>();
    private ArrayList<String> sm_coms = new ArrayList<>();
    private ArrayList<String> sm_coms2 = new ArrayList<>();
    private ArrayList<Integer> sm_content_image = new ArrayList<>();

    RecyclerViewAdapterForSM SmRecycler;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialmedia);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(toolbarClickListener());
        fillLists();


        swipeRefreshLayout = findViewById(R.id.rl_social_media);

        swipeRefreshLayout.setOnRefreshListener(helperFunctions.refreshListener(this, swipeRefreshLayout));

        FloatingActionButton addItem = findViewById(R.id.btn_addSmItem);
        addItem.setOnClickListener(addItemListener());
    }




    private View.OnClickListener addItemListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Socialmedia.this);
                alertDialogBuilder.setView(R.layout.add_sm);

                alertDialogBuilder.setCancelable(true).setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        EditText postName = ((Dialog) dialog).findViewById(R.id.et_postname);
                        EditText postDescr = ((Dialog) dialog).findViewById(R.id.et_descofitem);



                        if(!(postName.getText().toString().isEmpty()||
                                postDescr.getText().toString().isEmpty()
                                )){

                            if( helperFunctions.addItemSocialMedia(postName.getText().toString(), postDescr.getText().toString())){
                                imagePaths.add(R.drawable.ic_for_sale_24dp);
                                sm_names.add("UserName");
                                sm_names2.add("UserDepartment");
                                sm_coms.add(postDescr.getText().toString());
                                sm_coms2.add(postName.getText().toString());
                                sm_content_image.add(R.drawable.logo_name);

                                SmRecycler.notifyDataSetChanged();
                            }else{
                                Toast.makeText(Socialmedia.this, "Server Error", Toast.LENGTH_LONG).show();
                            }



                        }else{
                            Toast.makeText(Socialmedia.this, "Can not be added", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        };
    }



    private void fillLists(){

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerSM);
        SmRecycler = new RecyclerViewAdapterForSM(sm_names,sm_names2, sm_coms, sm_coms2, imagePaths, sm_content_image,this);
        recyclerView.setAdapter(SmRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private View.OnClickListener toolbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

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
}
