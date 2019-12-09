package com.example.test;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDao {
    @Query("select * from StepData")
    List<StepData> getAll();

    @Query("select * from StepData where user == :userId")
    List<StepData> findByUserId(int userId);

    @Query("select * from stepdata where id == :id limit 1")
    StepData findById(int id);

    @Insert
    void insertAll(StepData... step);

    @Insert
    long insert(StepData step);

    @Delete
    void delete(StepData step);

    @Update(onConflict = REPLACE)
    void updateStep(StepData step);

    @Query("delete from StepData")
    void deleteAll();
};
