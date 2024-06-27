package com.sovattanasamboleap.stacklab.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sovattanasamboleap.stacklab.Models.ItemModel;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    long insertItem(ItemModel item);

    @Query("SELECT * FROM items WHERE category = :categoryName")
    List<ItemModel> getItemsForCategory(String categoryName);

}
