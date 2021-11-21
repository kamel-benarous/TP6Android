package com.example.tp6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity{

    private TextView listHeader;

    private int[] bgArray ={
            R.color.primaryColor,
            R.color.primaryDarkColor,
            R.color.primaryLightColor,
            R.color.secondaryColor,
            R.color.secondaryDarkColor,
            R.color.secondaryLightColor
    };

    private RecyclerView pojoRecyclerView;
    private PojoAdapter pojoAdapter;
    private List<POJO> pojoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listHeader = (TextView) findViewById(R.id.listHeader);
        listHeader.setOnClickListener(onShowPopupMenu());


        pojoList = new ArrayList<POJO>();

        /*{{
            add(new POJO("Tunisia"));
            add(new POJO("Marroc"));
            add(new POJO("Japon"));
            add(new POJO("Malysia"));
            add(new POJO("Singapore"));
        }};*/

        String [] raw = getResources().getStringArray(R.array.pays);
        for(String pays:raw){
            pojoList.add(new POJO(pays));
        }

        pojoAdapter = new PojoAdapter(this ,pojoList);

        pojoRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        pojoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pojoRecyclerView.setAdapter(pojoAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.addBtn){
            Toast.makeText(getApplicationContext(), "add btn pressed", Toast.LENGTH_SHORT).show();
            showAddingDialog();
            return true;
        }

        if(item.getItemId() == R.id.sortAsc){
            Toast.makeText(getApplicationContext(), "sort asc btn pressed", Toast.LENGTH_SHORT).show();
            pojoAdapter.sortByName();
            return true;
        }

        if(item.getItemId() == R.id.sortDsc){
            Toast.makeText(getApplicationContext(), "sort dsc pressed", Toast.LENGTH_SHORT).show();
            pojoAdapter.reverseByName();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete){
            pojoAdapter.removeItem(item.getGroupId());
            Toast.makeText(getApplicationContext(), "delete clicked", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.gotowiki){
            String url = "https://en.wikipedia.org/wiki/" + pojoAdapter.pojoList.get(item.getGroupId()).getNom();
            pojoAdapter.openBrowser(url);
            Toast.makeText(getApplicationContext(), "go to wiki clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);
    }

    public void showAddingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        View dialogView = getLayoutInflater().inflate(R.layout.add_alert_dialog, null);
        EditText added = (EditText) dialogView.findViewById(R.id.added);

        builder.setView(dialogView);
        builder.setTitle("Ajouter nouveau pays");

        builder.setNegativeButton(
                "annuler",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
        });

        builder.setPositiveButton(
                "valider",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pojoAdapter.addItem(added.getText().toString());
                    }
                }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public View.OnClickListener onShowPopupMenu(){

        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(onPopupMenuClickListener());
                popupMenu.show();
            }
        };

    }

    public PopupMenu.OnMenuItemClickListener onPopupMenuClickListener(){
        return new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.showHide){
                    pojoRecyclerView.setVisibility(
                            pojoRecyclerView.getVisibility()==View.GONE?View.VISIBLE:View.GONE
                    );
                }

                if(item.getItemId() == R.id.changeBg){
                    listHeader.setBackgroundColor(bgArray[(int)(System.currentTimeMillis() % bgArray.length)]);
                }

                return true;
            }
        };
    }

}