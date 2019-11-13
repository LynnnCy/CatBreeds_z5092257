package com.example.catbreeds;

import com.example.catbreeds.model.BreedList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Database {
    public static HashMap<String, BreedList> savedBreedList = new HashMap<>();

    public static ArrayList<BreedList> favouriteBreedList = new ArrayList<BreedList>();

    public static BreedList getBreedById(String id){return savedBreedList.get(id);}

    public static void saveBreedList(List<BreedList> breedList) {
        for (int i = 0; i < breedList.size(); i++) {
            BreedList cat = breedList.get(i);
            savedBreedList.put(cat.getId(), cat);
        }
    }

    public static List<BreedList> filterList(String query){
        List<BreedList> currentList = new ArrayList<BreedList>(savedBreedList.values());
        List<BreedList> list = new ArrayList<BreedList>();
        for(int i = 0; i < currentList.size(); i++){
            String breedName = currentList.get(i).getName().toLowerCase();
            if(breedName.contains(query.toLowerCase())){
                list.add(currentList.get(i));
            }
        }
        return list;
    }

    public static int checkDuplicatedFavourite(String id){
        for(int i = 0; i < favouriteBreedList.size(); i++){
            BreedList breedList = favouriteBreedList.get(i);
            if(breedList.getId().equals(id)){
                return i;
            }
        }

        return -1;
    }
}
