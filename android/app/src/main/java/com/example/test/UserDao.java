package com.example.test;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Query("select * from userData")
    List<userData> getAll();
    @Query("select * from userData where username == :username limit 1")
    userData findByUsername(String username);
    @Insert
    void insertAll(userData ... userData);
    @Insert
    long insert(userData userData);
    @Delete
    void delete(userData userData);
    @Update(onConflict = REPLACE)
    void updateStep(userData userData);
    @Query("delete from userData")
    void deleteAll();
}
