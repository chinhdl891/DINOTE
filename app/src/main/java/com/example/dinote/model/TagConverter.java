package com.example.dinote.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class TagConverter {
    @TypeConverter
    public String getStringFromList(List<Tag> tagList) {
        return new Gson().toJson(tagList);
    }

    @TypeConverter
    public List<Tag> tagList(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Tag>>(){}.getType();
        Collection<Tag> enums = gson.fromJson(json, collectionType);
        return (List<Tag>) enums;

    }


}
