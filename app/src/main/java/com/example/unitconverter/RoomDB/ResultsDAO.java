package com.example.unitconverter.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResultsDAO {
    @Insert
    void insert(Results results);

    @Query("SELECT * FROM results")
    List<Results> getAll();

    @Query("Select * from results where id = :id")
    Results getById(int id);

    @Delete
    void delete(Results results);
}
