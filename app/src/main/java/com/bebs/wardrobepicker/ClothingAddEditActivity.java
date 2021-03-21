package com.bebs.wardrobepicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClothingAddEditActivity extends AppCompatActivity {

    private EditText editName, editDescription;
    private TextView txtTitleEdit;
    private Spinner SpinnerClothType;
    private CheckBox CheckBoxSeason0, CheckBoxSeason1, CheckBoxSeason2,
            CheckBoxSeason3, CheckBoxSeasonSpook, CheckBoxLaundry;
    private Button btnSave, btnCancel, btnDelClothing;

    private Clothing editable;

    private ClothingViewModel mClothingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_add_edit);

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
        if (gottenExtras) {
            Clothing toBeEditedClothing = (Clothing) intent.getExtras().get("Clothing");
            this.editable = toBeEditedClothing;
            try {
                initViews(toBeEditedClothing);
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSave.setOnClickListener(view -> {
                Intent replyIntent = new Intent();
                if (!ClothingIsValid()) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Clothing clothing = null;
                    try {
                        clothing = createClothing(toBeEditedClothing);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    replyIntent.putExtra("ClothingToBeSaved", clothing);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            });
        } else {
            try {
                initViews(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSave.setOnClickListener(view -> {
                Intent replyIntent = new Intent();
                if (!ClothingIsValid()) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Clothing clothing = null;
                    try {
                        clothing = createClothing(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    replyIntent.putExtra("ClothingToBeSaved", clothing);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            });
        }

        btnCancel.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_FIRST_USER, replyIntent);
            finish();
        });

        btnDelClothing.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete this Outfit?");
            builder.setPositiveButton("Yes", (dialog, which) -> { mClothingViewModel.deleteClothing(editable); finish(); })
                    .setNegativeButton("Cancel", (dialog, which) -> {});
            builder.create().show();
        });
    }

    private boolean ClothingIsValid(){
        List<CheckBox> Seasons = getSeasonCheckBoxList();
        boolean atLeastOneSeasonChecked = false;
        boolean nameIsSet = false;
        boolean typeIsSelected = false;
        for (CheckBox checkBox:Seasons) {
            if (checkBox.isChecked()) {
                atLeastOneSeasonChecked = true;
                break;
            }
        }
        if (!TextUtils.isEmpty(editName.getText())) {
            nameIsSet = true;
        }
        if (SpinnerClothType.getSelectedItem() != null) {
            typeIsSelected = true;
        }
        return atLeastOneSeasonChecked && nameIsSet && typeIsSelected;
    }

    private Clothing createClothing(Clothing editable) throws ExecutionException, InterruptedException {
        int selectedType = SpinnerClothType.getSelectedItemPosition();
        String title = editName.getText() != null ? editName.getText().toString() : "";
        String description = editDescription.getText() != null ? editDescription.getText().toString() : "";

        ArrayList<Integer> season = new ArrayList<>();
        List<CheckBox> seasonsList = getSeasonCheckBoxList();
        for (int i = 0; i < seasonsList.size(); i++) {
            season.add(seasonsList.get(i).isChecked()? 1 : 0);
        }
        int seasonInt = getSeasonId(season);

        Boolean inLaundry = CheckBoxLaundry.isChecked();

        Clothing clothing = new Clothing(selectedType, title, seasonInt);
        if (editable != null){
            clothing.setUid(editable.getUid());
        }
        clothing.setDescription(description);
        clothing.setDirty(inLaundry);
        return clothing;
    }

    private int getSeasonId(ArrayList<Integer> seasons) throws ExecutionException, InterruptedException {
        List<Integer> seasonIdInts = mClothingViewModel.getSeasonIdWithGivenSeasons(seasons.get(0), seasons.get(1), seasons.get(2), seasons.get(3), seasons.get(4));
        if (seasonIdInts.size() == 0) {
            Season season = new Season(seasons.get(0), seasons.get(1), seasons.get(2), seasons.get(3), seasons.get(4));
            mClothingViewModel.insertSeason(season);
            return mClothingViewModel.getSeasonIdWithGivenSeasons(seasons.get(0), seasons.get(1), seasons.get(2), seasons.get(3), seasons.get(4)).get(0);
        } else {
            return seasonIdInts.get(0);
        }

    }

    private List<CheckBox> getSeasonCheckBoxList() {
        List<CheckBox> res = new ArrayList<>();
        res.add(CheckBoxSeason0);
        res.add(CheckBoxSeason1);
        res.add(CheckBoxSeason2);
        res.add(CheckBoxSeason3);
        res.add(CheckBoxSeasonSpook);

        return res;
    }

    private void initSpinner(){
        Clothing clothing = new Clothing(0, "", 1);
        ArrayList<String> ClothingTypes = clothing.getTypeList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ClothingTypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerClothType.setAdapter(arrayAdapter);
    }

    private void initViews(Clothing editable) throws ExecutionException, InterruptedException {
        editName = findViewById(R.id.editName);
        editDescription = findViewById(R.id.editDescription);

        txtTitleEdit = findViewById(R.id.txtTitleEdit);

        SpinnerClothType = findViewById(R.id.SpinnerClothType);

        mClothingViewModel = new ViewModelProvider(this).get(ClothingViewModel.class);

        initSpinner();

        CheckBoxSeason0 = findViewById(R.id.CheckBoxSeason0);
        CheckBoxSeason1 = findViewById(R.id.CheckBoxSeason1);
        CheckBoxSeason2 = findViewById(R.id.CheckBoxSeason2);
        CheckBoxSeason3 = findViewById(R.id.CheckBoxSeason3);
        CheckBoxSeasonSpook = findViewById(R.id.CheckBoxSeasonSpook);
        CheckBoxLaundry = findViewById(R.id.CheckBoxLaundry);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelClothing = findViewById(R.id.btnDelClothing);

        List<CheckBox> checkBoxes = getSeasonCheckBoxList();

        if (editable != null) {
            editName.setText(editable.getName());
            editDescription.setText(editable.getDescription());
            SpinnerClothType.setSelection(editable.getType());
            List<Season> seasonList = mClothingViewModel.getSeasonById(editable.getSeason());
            if (seasonList.size() > 0) {
                ArrayList<Integer> seasonInts = seasonList.get(0).getSeasons();
                for (int i = 0; i < 5; i++) {
                    checkBoxes.get(i).setChecked(seasonInts.get(i) == 1);
                }
            }
            if (editable.getDirty()) {
                CheckBoxLaundry.setChecked(true);
            }
        }
    }

    //TODO Implement Picture picker and allow for the addition of your own pictures
}