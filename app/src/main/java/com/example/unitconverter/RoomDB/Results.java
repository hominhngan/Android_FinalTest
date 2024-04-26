package com.example.unitconverter.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Results {
    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo (name = "result")
    public double result;


}


