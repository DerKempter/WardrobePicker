package com.bebs.wardrobepicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OutfitHistoryEditDelActivity extends AppCompatActivity implements ClothingRecViewAdapter.OnClothingListener {

    private Button btnOutfitHistoryCancel, btnOutfitHistorySave, btnDelOutfit;

    private EditText editTextOutfitNamePost;

    private RecyclerView outfitHistoryRecyclerView;

    private Outfit editable;

    ClothingViewModel mClothingViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_del_edit);

        Intent intent = getIntent();
        boolean gottenExtras = false;

        try {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                gottenExtras = true;
            }
        }catch (Exception e){
            Toast.makeText(this, "Couldn't fetch intent of Activity.", Toast.LENGTH_SHORT).show();
        }

        if (gottenExtras){
            Outfit toBeEditedOutfit = (Outfit) intent.getExtras().get("Outfit");
            editable = toBeEditedOutfit;
            try {
                initViews(toBeEditedOutfit);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            btnOutfitHistorySave.setOnClickListener(view -> {
                Intent replyIntent = new Intent();
                if (!outfitIsValid()) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Outfit outfit = createOutfit(toBeEditedOutfit);
                    replyIntent.putExtra("OutfitToBeSaved", outfit);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            });
        } else {
            try {
                initViews(null);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            btnOutfitHistorySave.setOnClickListener(view -> {
                Intent replyIntent = new Intent();
                if (!outfitIsValid()) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Outfit outfit = createOutfit(null);
                    replyIntent.putExtra("OutfitToBeSaved", outfit);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            });
        }

        btnOutfitHistoryCancel.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_FIRST_USER, replyIntent);
            finish();
        });

        btnDelOutfit.setOnClickListener(View -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete this Outfit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { mClothingViewModel.deleteOutfit(editable); finish(); }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            builder.create().show();
        });
    }

    private boolean outfitIsValid(){
        return true;
    }

    private Outfit createOutfit(Outfit outfit){
        outfit.setName(editTextOutfitNamePost.getText().toString());
        return outfit;
    }

    private void initViews(Outfit editable) throws ExecutionException, InterruptedException {
        btnOutfitHistorySave = findViewById(R.id.btnOutfitHistorySave);
        btnOutfitHistoryCancel = findViewById(R.id.btnOutfitHistoryCancel);
        btnDelOutfit = findViewById(R.id.btnDelOutfit);

        editTextOutfitNamePost = findViewById(R.id.editTextOutfitNamePost);

        outfitHistoryRecyclerView = findViewById(R.id.outfitHistoryRecyclerView);

        ClothingRecViewAdapter adapter = new ClothingRecViewAdapter(this);
        mClothingViewModel = new ViewModelProvider(this).get(ClothingViewModel.class);

        if (editable != null){
            editTextOutfitNamePost.setText(editable.getName());

            List<Integer> clothId = editable.getClothes();
            List<Clothing> clothes = new ArrayList<>();

            for (int uuid : clothId) {
                if (uuid != 0){
                    clothes.add(mClothingViewModel.getById(uuid));
                }
            }
            adapter.setClothing(clothes);

            outfitHistoryRecyclerView.setAdapter(adapter);
            outfitHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    @Override
    public void onClothingClick(int position) {

    }
}
