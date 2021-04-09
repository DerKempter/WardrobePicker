package com.bebs.wardrobepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AllClothingActivity extends AppCompatActivity implements ClothingRecViewAdapter.OnClothingListener {

    private RecyclerView clothingRecyclerView;
    private FloatingActionButton fab;
    Button btnBack, btnFilter, btnResetFilter, btnUncheckAllFilter;

    private MaterialCheckBox checkBoxJacketFilter, checkBoxPulloverFilter, checkBoxSkirtFilter, checkBoxDressFilter,
            checkBoxPantsFilter, checkBoxShortsFilter, checkBoxJumpsuitFilter, checkBoxShoesFilter, checkBoxCropTopFilter, checkBoxBeltFilter,
            checkBoxShirtFilter, checkBoxHoodieFilter, checkBoxSpringFilter, checkBoxSummerFilter, checkBoxAutumnFilter, checkBoxWinterFilter,
            checkBoxSpookFilter, checkBoxJeweleryFilter;

    List<MaterialCheckBox> checkBoxes;
    List<MaterialCheckBox> seasonBoxes;

    private List<Clothing> clothing;
    private List<Clothing> rawClothing;

    private ClothingViewModel mClothingViewModel;

    public static final int ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE = 1;
    public static final int ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE_UPDATE = 2;

    View filterDialogLayout;
    AlertDialog dialog;

    ClothingRecViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clothing);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());
        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AllClothingActivity.this, ClothingAddEditActivity.class);
            startActivityForResult(intent, ADD_EDIT_CLOTHING_ACTIVITY_REQUEST_CODE);
        });

        btnFilter = findViewById(R.id.btnFilter);
        LayoutInflater inflater = getLayoutInflater();
        filterDialogLayout = inflater.inflate(R.layout.dialog_apply_filter, null);

        initRCView();

    }

    public void onBtnFilterClick(View view){
        if (this.dialog != null) {
            dialog.show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(filterDialogLayout);
        builder.setTitle("Chose Filters to apply:");
        builder.setPositiveButton("Apply", (dialog, which) -> {
            try {
                applyFilter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        })
                .setNegativeButton("Cancel", (dialog, which) -> {

                });
        this.dialog = builder.create();
        this.dialog.show();

        initDialogView();
    }

    private void applyFilter() throws ExecutionException, InterruptedException {
        List<Integer> filteredClothingTypes = new ArrayList<>();
        List<Integer> filteredSeasons = new ArrayList<>();

        for (MaterialCheckBox checkBox : this.checkBoxes) {
            if (checkBox.isChecked()) {
                filteredClothingTypes.add(this.checkBoxes.indexOf(checkBox));
            }
        }
        for (MaterialCheckBox checkBox : this.seasonBoxes) {
            if (checkBox.isChecked()) {
                filteredSeasons.add(1);
            }
            else filteredSeasons.add(0);
        }
        Season filterSeason = new Season(filteredSeasons.get(0), filteredSeasons.get(1), filteredSeasons.get(2), filteredSeasons.get(3), filteredSeasons.get(4));

        List<Season> seasons = mClothingViewModel.getAllSeasons();

        this.clothing = new ArrayList<>();

        for (Clothing clothing : this.rawClothing) {
            Season tempSeason = seasons.get(clothing.getSeason());
            if (filteredClothingTypes.contains(clothing.getType()) && filterSeason.partEquals(tempSeason)) {
                this.clothing.add(clothing);
            }
        }

        adapter.setClothing(this.clothing);

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

    private void initDialogView(){

        btnFilter = findViewById(R.id.btnFilter);
        LayoutInflater inflater = getLayoutInflater();
        filterDialogLayout = inflater.inflate(R.layout.dialog_apply_filter, null);


        checkBoxJacketFilter = dialog.findViewById(R.id.CheckBoxJacketFilter);
        checkBoxPulloverFilter = dialog.findViewById(R.id.CheckBoxPulloverFilter);
        checkBoxSkirtFilter = dialog.findViewById(R.id.CheckBoxSkirtFilter);
        checkBoxDressFilter = dialog.findViewById(R.id.CheckBoxDressFilter);
        checkBoxPantsFilter = dialog.findViewById(R.id.CheckBoxPantsFilter);
        checkBoxShortsFilter = dialog.findViewById(R.id.CheckBoxShortsFilter);
        checkBoxJumpsuitFilter = dialog.findViewById(R.id.CheckBoxJumpsuitFilter);
        checkBoxShoesFilter = dialog.findViewById(R.id.CheckBoxShoesFilter);
        checkBoxCropTopFilter = dialog.findViewById(R.id.CheckBoxCropTopFilter);
        checkBoxBeltFilter = dialog.findViewById(R.id.CheckBoxBeltFilter);
        checkBoxShirtFilter = dialog.findViewById(R.id.CheckBoxShirtFilter);
        checkBoxHoodieFilter = dialog.findViewById(R.id.CheckBoxHoodieFilter);
        checkBoxSpringFilter = dialog.findViewById(R.id.checkBoxSpringFilter);
        checkBoxSummerFilter = dialog.findViewById(R.id.checkBoxSummerFilter);
        checkBoxAutumnFilter = dialog.findViewById(R.id.checkBoxAutumnFilter);
        checkBoxWinterFilter = dialog.findViewById(R.id.checkBoxWinterFilter);
        checkBoxSpookFilter = dialog.findViewById(R.id.checkBoxSpookFilter);
        checkBoxJeweleryFilter = dialog.findViewById(R.id.CheckBoxJeweleryFilter);

        btnResetFilter = dialog.findViewById(R.id.btnResetFilter);
        btnUncheckAllFilter = dialog.findViewById(R.id.btnUncheckAllFilter);

        btnResetFilter.setOnClickListener(view -> {
            resetFilter(true);
        });

        btnUncheckAllFilter.setOnClickListener(view -> {
            resetFilter(false);
        });

        this.checkBoxes = new ArrayList<>();
        this.seasonBoxes = new ArrayList<>();

        checkBoxes.add(checkBoxPulloverFilter);
        checkBoxes.add(checkBoxSkirtFilter);
        checkBoxes.add(checkBoxDressFilter);
        checkBoxes.add(checkBoxPantsFilter);
        checkBoxes.add(checkBoxShortsFilter);
        checkBoxes.add(checkBoxJumpsuitFilter);
        checkBoxes.add(checkBoxJacketFilter);
        checkBoxes.add(checkBoxShoesFilter);
        checkBoxes.add(checkBoxJeweleryFilter);
        checkBoxes.add(checkBoxBeltFilter);
        checkBoxes.add(checkBoxShirtFilter);
        checkBoxes.add(checkBoxHoodieFilter);
        checkBoxes.add(checkBoxCropTopFilter);

        seasonBoxes.add(checkBoxSpringFilter);
        seasonBoxes.add(checkBoxSummerFilter);
        seasonBoxes.add(checkBoxAutumnFilter);
        seasonBoxes.add(checkBoxWinterFilter);
        seasonBoxes.add(checkBoxSpookFilter);
    }

    private void resetFilter(boolean checkAll) {
        for (MaterialCheckBox checkBox : this.checkBoxes) {
            checkBox.setChecked(checkAll);
        }
        for (MaterialCheckBox checkBox : this.seasonBoxes) {
            checkBox.setChecked(checkAll);
        }
    }

    private void initRCView(){

        clothingRecyclerView = findViewById(R.id.clothingRecyclerView);
        adapter = new ClothingRecViewAdapter(this);

        mClothingViewModel = new ViewModelProvider(this).get(ClothingViewModel.class);

        mClothingViewModel.getAllClothes().observe(this, clothing -> {
            this.clothing = clothing;
            adapter.setClothing(this.clothing);
            this.rawClothing = this.clothing;
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