package com.bebs.wardrobepicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class OutfitHistoryActivity extends AppCompatActivity implements OutfitRecViewAdapter.OnOutfitListener {

    private RecyclerView outfitRecyclerView;
    private FloatingActionButton fab;
    private Button btnBack, btnFilter;

    private List<Outfit> outfits;

    private ClothingViewModel mClothingViewModel;

    public static final int DEL_EDIT_OUTFIT_ACTIVITY_REQUEST_CODE = 1;
    public static final int DEL_EDIT_OUTFIT_ACTIVITY_REQUEST_CODE_UPDATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_history);

        btnBack = findViewById(R.id.btnOutfitHistoryBack);
        btnFilter = findViewById(R.id.btnOutfitHistoryFilter);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(OutfitHistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnFilter.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Filters to apply:");
            builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { applyFilter(); }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            builder.create().show();
        });

        initRCView();
    }

    public void onActivityResult(int RequestCode, int ResultCode, Intent data){
        super.onActivityResult(RequestCode, ResultCode, data);

        if (RequestCode == DEL_EDIT_OUTFIT_ACTIVITY_REQUEST_CODE_UPDATE && ResultCode == RESULT_OK) {
            Outfit outfit = (Outfit) data.getExtras().get("OutfitToBeSaved");
            mClothingViewModel.updateOutfit(outfit);
        }
    }

    private void applyFilter(){
        //TODO Actually Apply Filters and design the Dialog.xml
    }

    private void initRCView(){

        outfitRecyclerView = findViewById(R.id.outfitRecyclerView);
        OutfitRecViewAdapter adapter = new OutfitRecViewAdapter(this);

        mClothingViewModel = new ViewModelProvider(this).get(ClothingViewModel.class);

        mClothingViewModel.getAllOutfits().observe(this, outfits -> {
            adapter.setOutfit(outfits);
            this.outfits = outfits;
        });
        outfitRecyclerView.setAdapter(adapter);
        outfitRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onOutfitClick(int position) {
        Outfit selectedOutfit = mClothingViewModel.getAllOutfits().getValue().get(position);
        Intent intent = new Intent(OutfitHistoryActivity.this, OutfitHistoryEditDelActivity.class);
        intent.putExtra("Outfit", selectedOutfit);
        startActivityForResult(intent, DEL_EDIT_OUTFIT_ACTIVITY_REQUEST_CODE_UPDATE);
    }
}