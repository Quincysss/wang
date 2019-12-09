package com.example.test;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepDao {
    @Query("select * from stepsData")
    List<stepsData> getAll();
    @Query("select * from stepsData where step == :steps limit 1")
    stepsData findBySteps(double steps);
    @Query("select * from stepsData where id == :id limit 1")
    stepsData findById(int id);
    @Insert
    void insertAll(stepsData ... steps);
    @Insert
    long insert(stepsData steps);
    @Delete
    void delete(stepsData steps);
    @Update(onConflict = REPLACE)
    void updateStep(stepsData steps);
    @Query("delete from stepsData")
    void deleteAll();
}
