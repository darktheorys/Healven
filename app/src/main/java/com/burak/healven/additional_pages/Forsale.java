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
import com.burak.healven.helpful.RecyclerViewAdapterForSale;
import com.burak.healven.helpful.helperFunctions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Forsale extends AppCompatActivity {

    private ArrayList<Integer> imagePaths= new ArrayList<>();
    private ArrayList<String> itemNames1 = new ArrayList<>();
    private ArrayList<String> itemNames2 = new ArrayList<>();
    private ArrayList<String> descriptions1 = new ArrayList<>();
    private ArrayList<String> descriptions2 = new ArrayList<>();

    RecyclerViewAdapterForSale FsRecycler;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forsale);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(toolbarClickListener());

        fillLists();

        swipeRefreshLayout = findViewById(R.id.rl_for_sale);

        swipeRefreshLayout.setOnRefreshListener(helperFunctions.refreshListener(this, swipeRefreshLayout));

        FloatingActionButton addItem = findViewById(R.id.btn_addForSaleItem);


        addItem.setOnClickListener(addItemListener());
    }

    private View.OnClickListener addItemListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Forsale.this);
                alertDialogBuilder.setView(R.layout.add_forsale);

                alertDialogBuilder.setCancelable(true).setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        EditText itemName = ((Dialog) dialog).findViewById(R.id.et_nameofitem);
                        EditText userName = ((Dialog) dialog).findViewById(R.id.et_nameofuser);
                        EditText descItem = ((Dialog) dialog).findViewById(R.id.et_descofitem);
                        EditText priceItem =  ((Dialog) dialog).findViewById(R.id.et_priceofitem);


                        if(!(itemName.getText().toString().isEmpty()||
                                userName.getText().toString().isEmpty() ||
                                descItem.getText().toString().isEmpty() ||
                                priceItem.getText().toString().isEmpty())){

                            if( helperFunctions.addItemForSale(itemName.getText().toString(), userName.getText().toString(), descItem.getText().toString(), priceItem.getText().toString())){
                                imagePaths.add(R.drawable.ic_for_sale_24dp);
                                itemNames1.add(itemName.getText().toString());
                                itemNames2.add(userName.getText().toString());
                                descriptions1.add(descItem.getText().toString());
                                descriptions2.add(priceItem.getText().toString());
                                FsRecycler.notifyDataSetChanged();
                            }else{
                                Toast.makeText(Forsale.this, "Server Error", Toast.LENGTH_LONG).show();
                            }



                        }else{
                            Toast.makeText(Forsale.this, "Can not be added", Toast.LENGTH_LONG).show();
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
        RecyclerView recyclerView = findViewById(R.id.recyclerForSale);
        FsRecycler = new RecyclerViewAdapterForSale(descriptions1,descriptions2, itemNames1, itemNames2, imagePaths,this);
        recyclerView.setAdapter(FsRecycler);
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
