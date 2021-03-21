package com.bebs.wardrobepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.common.collect.Sets;

import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;

public class OutfitPickerActivity extends AppCompatActivity implements ClothingRecViewAdapter.OnClothingListener {

    private ConstraintLayout constraintLayoutTop, constraintLayoutBottom, constraintLayoutRest, constraintLayoutResult, constraintLayoutSeasons;
    private Button btnShowResult, btnReturnToCreator, btnSaveOutfit, btnBack;
    private MaterialCheckBox checkBoxJacket, checkBoxPullover, checkBoxSkirt, checkBoxDress,
            checkBoxPants, checkBoxShorts, checkBoxJumpsuit, checkBoxShoes, checkBoxCropTop, checkBoxBelt,
            checkBoxShirt, checkBoxHoodie, checkBoxSpring, checkBoxSummer, checkBoxAutumn, checkBoxWinter,
            checkBoxSpook;
    private MaterialCheckBox[] checkBoxes, seasons;
    private Spinner spinnerJewelery;
    private RecyclerView resultRecyclerView;
    private EditText editTextOutfitNames;

    private ClothingViewModel mClothingViewModel;

    private List<Clothing> clothing;

    private ArrayList<String> seasonInt;
    private boolean laundry;
    private ArrayList<Integer> typeOfClothing;
    private ArrayList<String> seasonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_picker);

        initElements();

    }

    public void onBtnShowResultClick(View view) throws ExecutionException, InterruptedException {
        ArrayList<Integer> checks = convertCheckBoxToArray();
        boolean empty = true;
        for (int i = 0; i < checks.size(); i++) {
            if (checks.get(i) != 0) {
                empty = false;
            }
        }
        if (empty) {
            Toast.makeText(this, "Alle Felder sind leer", Toast.LENGTH_SHORT).show();
        } else {
            prepareForResults();
            initRCView();
        }
    }

    public void onBtnReturnToCreatorClick(View view) {
        prepareForCreator();
    }

    public void onBtnSaveOutfit(View view) {
        Outfit outfit = createOutfit(this.clothing);
        mClothingViewModel.insert(outfit);

        Toast.makeText(this, "Saved Outfit named "+ outfit.getName() +"!", Toast.LENGTH_SHORT).show();
    }

    private Outfit createOutfit(List<Clothing> clothes) {
        Outfit outfit = new Outfit();
        outfit.setCreated(Calendar.getInstance().getTime());
        outfit.setName(editTextOutfitNames.getText().toString());

        outfit.setSize(clothes.size());

        for (Clothing clothing : clothes) {
            int uuid = clothing.getUid();
            switch(clothing.getType()){
                case 0:
                    outfit.setPullover(uuid);
                    break;
                case 1:
                    outfit.setSkirt(uuid);
                    break;
                case 2:
                    outfit.setDress(uuid);
                    break;
                case 3:
                    outfit.setPants(uuid);
                    break;
                case 4:
                    outfit.setShorts(uuid);
                    break;
                case 5:
                    outfit.setJumpsuit(uuid);
                    break;
                case 6:
                    outfit.setJacket(uuid);
                    break;
                case 7:
                    outfit.setShoes(uuid);
                    break;
                case 8:
                    outfit.setJewelery(uuid);
                    break;
                case 9:
                    outfit.setBelt(uuid);
                    break;
                case 10:
                    outfit.setShirt(uuid);
                    break;
                case 11:
                    outfit.setHoodie(uuid);
                    break;
                case 12:
                    outfit.setCrop_top(uuid);
                    break;
            }
        }
        return outfit;
    }

    private ArrayList<Integer> convertCheckBoxToArray() {
        int[] result = new int[13];
        for (int i = 0; i < checkBoxes.length; i++) {
            if (i != 8) {
                result[i] = checkBoxes[i].isChecked() ? 1 : 0;
            } else {
                result[i] = spinnerJewelery.getSelectedItemPosition();
            }
        }
        ArrayList<Integer> intList = new ArrayList<>(result.length);
        for (int i : result)
        {
            intList.add(i);
        }
        return intList;
    }

    private ArrayList<Integer> convertCheckBoxToArrayNumerized() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            if (i != 8) {
                if(checkBoxes[i].isChecked())
                {
                    result.add(i);
                }
            } else {
                if (spinnerJewelery.getSelectedItemPosition() != 0)
                {
                    result.add(i);
                }
            }
        }
        return result;
    }

    private void prepareForResults() {
        constraintLayoutTop.setVisibility(View.GONE);
        constraintLayoutBottom.setVisibility(View.GONE);
        constraintLayoutRest.setVisibility(View.GONE);
        constraintLayoutSeasons.setVisibility(View.GONE);
        btnShowResult.setVisibility(View.GONE);
        spinnerJewelery.setVisibility(View.GONE);
        btnReturnToCreator.setVisibility(View.VISIBLE);
        btnSaveOutfit.setVisibility(View.VISIBLE);
        editTextOutfitNames.setVisibility(View.VISIBLE);
    }

    private void prepareForCreator() {
        btnReturnToCreator.setVisibility(View.GONE);
        btnSaveOutfit.setVisibility(View.GONE);
        editTextOutfitNames.setVisibility(View.GONE);
        resultRecyclerView.setVisibility(View.GONE);
        constraintLayoutTop.setVisibility(View.VISIBLE);
        constraintLayoutBottom.setVisibility(View.VISIBLE);
        constraintLayoutRest.setVisibility(View.VISIBLE);
        constraintLayoutSeasons.setVisibility(View.VISIBLE);
        btnShowResult.setVisibility(View.VISIBLE);
        spinnerJewelery.setVisibility(View.VISIBLE);
    }

    private void initElements() {
        constraintLayoutTop = findViewById(R.id.ConstraintLayoutTop);
        constraintLayoutBottom = findViewById(R.id.ConstraintLayoutBottom);
        constraintLayoutRest = findViewById(R.id.ConstraintLayoutRest);
        constraintLayoutSeasons = findViewById(R.id.constraintLayoutSeasons);
        spinnerJewelery = findViewById(R.id.SpinnerJewelery);

        editTextOutfitNames = findViewById(R.id.editTextOutfitName);

        btnShowResult = findViewById(R.id.btnShowResult);
        btnReturnToCreator = findViewById(R.id.btnReturnToCreator);
        btnSaveOutfit = findViewById(R.id.btnSaveOutfit);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());

        initCheckBoxes();
        initSpinner();

        btnReturnToCreator.setVisibility(View.GONE);
        btnSaveOutfit.setVisibility(View.GONE);
        editTextOutfitNames.setVisibility(View.GONE);
    }

    private void initSpinner() {
        ArrayList<String> amntJewelery = new ArrayList<>();
        amntJewelery.add("No Jewelery");
        amntJewelery.add("One Accessory");
        amntJewelery.add("Two Accessories");
        amntJewelery.add("Three Accessories");

        ArrayAdapter<String> jeweleryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                amntJewelery
        );
        spinnerJewelery.setAdapter(jeweleryAdapter);
    }

    private void initCheckBoxes() {
        checkBoxJacket = findViewById(R.id.CheckBoxJacket);
        checkBoxPullover = findViewById(R.id.CheckBoxPullover);
        checkBoxSkirt = findViewById(R.id.CheckBoxSkirt);
        checkBoxDress = findViewById(R.id.CheckBoxDress);
        checkBoxPants = findViewById(R.id.CheckBoxPants);
        checkBoxShorts = findViewById(R.id.CheckBoxShorts);
        checkBoxJumpsuit = findViewById(R.id.CheckBoxJumpsuit);
        checkBoxShoes = findViewById(R.id.CheckBoxShoes);
        checkBoxCropTop = findViewById(R.id.CheckBoxCropTop);
        checkBoxBelt = findViewById(R.id.CheckBoxBelt);
        checkBoxShirt = findViewById(R.id.CheckBoxShirt);
        checkBoxHoodie = findViewById(R.id.CheckBoxHoodie);

        checkBoxes = new MaterialCheckBox[13];

        checkBoxes[0] = checkBoxPullover;
        checkBoxes[1] = checkBoxSkirt;
        checkBoxes[2] = checkBoxDress;
        checkBoxes[3] = checkBoxPants;
        checkBoxes[4] = checkBoxShorts;
        checkBoxes[5] = checkBoxJumpsuit;
        checkBoxes[6] = checkBoxJacket;
        checkBoxes[7] = checkBoxShoes;
        checkBoxes[9] = checkBoxBelt;
        checkBoxes[10] = checkBoxShirt;
        checkBoxes[11] = checkBoxHoodie;
        checkBoxes[12] = checkBoxCropTop;

        checkBoxSpring = findViewById(R.id.checkBoxSpring);
        checkBoxSummer = findViewById(R.id.checkBoxSummer);
        checkBoxAutumn = findViewById(R.id.checkBoxAutumn);
        checkBoxWinter = findViewById(R.id.checkBoxWinter);
        checkBoxSpook = findViewById(R.id.checkBoxSpook);

        seasons = new MaterialCheckBox[5];

        seasons[0] = checkBoxSpring;
        seasons[1] = checkBoxSummer;
        seasons[2] = checkBoxAutumn;
        seasons[3] = checkBoxWinter;
        seasons[4] = checkBoxSpook;
    }

    private void getSelections() {
        seasonInt = new ArrayList<>();
        ArrayList<String> seasonDefaultInt = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {
            seasonDefaultInt.add(Integer.toString(i));
        }
        for (int i = 0; i < seasons.length; i++) {
            if (seasons[i].isChecked()){
                seasonInt.add(Integer.toString(i));
            }
        }

        if (seasonInt.size() == 0) {
            seasonInt = seasonDefaultInt;
        }

        laundry = false;

        typeOfClothing = convertCheckBoxToArrayNumerized();

        ArrayList<Integer> seasons = new ArrayList<>();

        if (seasonInt.contains(0)) {
            seasons.add(1);
        } else {
            seasons.add(0);
        }
        if (seasonInt.contains(1)) {
            seasons.add(1);
        } else {
            seasons.add(0);
        }
        if (seasonInt.contains(2)) {
            seasons.add(1);
        } else {
            seasons.add(0);
        }
        if (seasonInt.contains(3)) {
            seasons.add(1);
        } else {
            seasons.add(0);
        }
        if (seasonInt.contains(4)) {
            seasons.add(1);
        } else {
            seasons.add(0);
        }

        //TODO Request all season object, build a season object with selected seasons and compare. If equal is found, take this seasons Id for the Clothing Request.

        Set<String> seasonSet = new HashSet<>(seasonInt);
        seasonList = new ArrayList<>();
        Set<String> seasonDefaultSet = new HashSet<>(seasonDefaultInt);
        Set<Set<String>> setOfSets = Sets.powerSet(seasonDefaultSet);

        for (Set<String> set : setOfSets) {
            if (set.stream().anyMatch(seasonSet::contains))
            {
                seasonList.add(String.join("", set));
            }
        }
    }


    private void initRCView() throws ExecutionException, InterruptedException {

        //TODO make this actually randomize the result and not just pull all results

        getSelections();

        resultRecyclerView = findViewById(R.id.ResultRecyclerView);

        ClothingRecViewAdapter adapter = new ClothingRecViewAdapter(this);

        mClothingViewModel = new ViewModelProvider(this).get(ClothingViewModel.class);

        List<List<Clothing>> clothingListCollAll = new ArrayList<>();
        List<Clothing> clothingListRandomized = new ArrayList<>();

        List<Integer> toBeRemovedFromTypeOfClothing = new ArrayList<>();

        for (int clothingType : typeOfClothing) {
            ArrayList<Integer> clothTypeArray = new ArrayList<>();
            clothTypeArray.add(clothingType);
            List<Clothing> tempClothes = mClothingViewModel.getWithFullFilter(seasonList, laundry, clothTypeArray);
            if (tempClothes.size() == 0) {
                toBeRemovedFromTypeOfClothing.add(clothingType);
            }
            clothingListCollAll.add(tempClothes);
        }

        for (Integer clothingType : toBeRemovedFromTypeOfClothing) {
            typeOfClothing.remove(clothingType);
        }

        this.typeOfClothing = filterClothingCats(this.typeOfClothing);

        clothingListCollAll = new ArrayList<>();

        for (int clothingType : typeOfClothing) {
            ArrayList<Integer> clothTypeArray = new ArrayList<>();
            clothTypeArray.add(clothingType);
            List<Clothing> tempClothes = mClothingViewModel.getWithFullFilter(seasonList, laundry, clothTypeArray);
            clothingListCollAll.add(tempClothes);
        }

        for (List<Clothing> clothingList : clothingListCollAll) {
            if (clothingList.size() > 0){
                int randomNum = ThreadLocalRandom.current().nextInt(0, Math.max(clothingList.size(),1));
                clothingListRandomized.add(clothingList.get(randomNum));
            }
        }

        this.clothing = clothingListRandomized;
        adapter.setClothing(this.clothing);

        resultRecyclerView.setAdapter(adapter);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        resultRecyclerView.setVisibility(View.VISIBLE);
    }

    public ArrayList<Integer> filterClothingCats(List<Integer> typesOfClothes){
        ArrayList<Integer> resTypes = new ArrayList<>();

        List<Integer> clothCatRest = Arrays.asList(6,7,8,9);
        List<Integer> clothCatBottoms = Arrays.asList(1,3,4);
        List<Integer> clothCatTop = Arrays.asList(0,10,11,12);
        List<Integer> clothCatMiddle = Arrays.asList(2,5);
        List<List<Integer>> possibilityLists = new ArrayList<>();
        possibilityLists.add(clothCatTop);
        possibilityLists.add(clothCatMiddle);
        possibilityLists.add(clothCatBottoms);

        List<Integer> possibleBottoms = new ArrayList<>();
        List<Integer> possibleTops = new ArrayList<>();
        List<Integer> possibleMiddles = new ArrayList<>();
        List<List<Integer>> possibleLists = new ArrayList<>();
        possibleLists.add(possibleTops);
        possibleLists.add(possibleMiddles);
        possibleLists.add(possibleBottoms);


        for (List<Integer> posList : possibilityLists) {
            for (Integer type : posList) {
                if (typesOfClothes.contains(type)) {
                    possibleLists.get(possibilityLists.indexOf(posList)).add(type);
                }
            }
        }


        if ((possibleLists.get(0).size() > 0 || possibleLists.get(2).size() > 0) && possibleLists.get(1).size() > 0) {
            int coinFlip = ThreadLocalRandom.current().nextInt(0,2);
            if (coinFlip == 0) {
                possibleLists.remove(1);
            } else {
                possibleLists.remove(2);
                possibleLists.remove(0);
            }
        }

        for (List<Integer> typeList : possibleLists) {
            if (typeList.size() >= 1) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, typeList.size());
                resTypes.add(typeList.get(randomNum));
            }
        }

        resTypes.addAll(clothCatRest);

        return resTypes;
    }

    @Override
    public void onClothingClick(int position) {

        //TODO make this open the Clothing viewer, but make it be not editable

        Clothing toBePassedClothing = clothing.get(position);
        Intent intent = new Intent(this, ClothingAddEditActivity.class);
        intent.putExtra("Clothing", toBePassedClothing);
        startActivity(intent);
    }

    //TODO add more Filters and add logic for combination of different Filters
}
