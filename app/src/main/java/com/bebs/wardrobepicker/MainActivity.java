package com.bebs.wardrobepicker;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import io.reactivex.Completable;
import io.reactivex.functions.Action;


public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";

    private ExecutorService executorService;

    private Button btnCollection, btnCreateOutfit, btnOutfitHistory;
    private RecyclerView clothingRecyclerView;

    ClothDao clothDao;

    Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clothDao = WardrobePicker.dbSetup.clothDatabase.clothDao();

        initViews();

        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllClothingActivity.class);
                startActivity(intent);
            }
        });

        btnCreateOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OutfitPickerActivity.class);
                startActivity(intent);
            }
        });

        btnOutfitHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OutfitHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBtnCollClick(View view){
        Toast.makeText(MainActivity.this, "Clicked on Collection Button", Toast.LENGTH_LONG).show();
    }

    public void onBtnCreateClick(View view){
        Toast.makeText(MainActivity.this, "Clicked on Create Outfit Button", Toast.LENGTH_LONG).show();
    }

    public void onBtnHistClick(View view){
        Toast.makeText(MainActivity.this, "Clicked on Outfit History Button", Toast.LENGTH_LONG).show();
        Clothing test = new Clothing(1, "I luv mine beb!!", 01);
        List<Clothing> clothes = new ArrayList<>();
        //Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
    }


    private void initViews(){
        btnCollection = findViewById(R.id.btnCollection);
        btnCreateOutfit = findViewById(R.id.btnCreateOutfit);
        btnOutfitHistory = findViewById(R.id.btnOutfitHistory);
    }

    //TODO Display last created Outfit somehow (Make button to display last Outfit etc.)..
}