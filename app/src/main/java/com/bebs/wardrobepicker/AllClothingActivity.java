package com.bebs.wardrobepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AllClothingActivity extends AppCompatActivity implements ClothingRecViewAdapter.OnClothingListener {

    private RecyclerView clothingRecyclerView;
    private FloatingActionButton fab;
    Button btnBack, btnFilter;

    private List<Clothing> clothing;

    private ClothingViewModel mClothingViewModel;

    public static final int ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE = 1;
    public static final int ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE_UPDATE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clothing);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AllClothingActivity.this, MainActivity.class);
            startActivity(intent);
        });
        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AllClothingActivity.this, ClothingAddEditActivity.class);
            startActivityForResult(intent, ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE);
        });

        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Chose Filters to apply:");
            builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    applyFilter();
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        });

        initRCView();

    }

    private void applyFilter(){
        //TODO Actually Apply Filters and design the Dialog.xml
    }

    public void onActivityResult(int RequestCode, int ResultCode, Intent data) {
        super.onActivityResult(RequestCode, ResultCode, data);

        if (RequestCode == ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE && ResultCode == RESULT_OK) {
            Clothing cloth = (Clothing) data.getExtras().get("ClothingToBeSaved");
            mClothingViewModel.insert(cloth);
        } else if (RequestCode == ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE_UPDATE && ResultCode == RESULT_OK) {
            Clothing cloth = (Clothing) data.getExtras().get("ClothingToBeSaved");
            mClothingViewModel.updateClothing(cloth);
        } else if ((RequestCode == ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE || RequestCode == ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE_UPDATE) && ResultCode == RESULT_FIRST_USER) {
            Toast.makeText(getApplicationContext(), "Canceled. No changes made.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "clothing wrongly formatted, not saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRCView(){

        clothingRecyclerView = findViewById(R.id.clothingRecyclerView);
        ClothingRecViewAdapter adapter = new ClothingRecViewAdapter(this);

        mClothingViewModel = new ViewModelProvider(this).get(ClothingViewModel.class);

        mClothingViewModel.getAllClothes().observe(this, clothing -> {
            adapter.setClothing(clothing);
            this.clothing = clothing;
        });
        clothingRecyclerView.setAdapter(adapter);
        clothingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClothingClick(int position) {
        Clothing selectedClothing = mClothingViewModel.getAllClothes().getValue().get(position);
        Intent intent = new Intent(AllClothingActivity.this, ClothingAddEditActivity.class);
        intent.putExtra("Clothing", selectedClothing);
        startActivityForResult(intent, ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE_UPDATE);
    }
}