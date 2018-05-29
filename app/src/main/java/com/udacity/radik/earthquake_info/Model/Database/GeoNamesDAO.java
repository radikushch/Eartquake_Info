package com.udacity.radik.earthquake_info.Model.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.udacity.radik.earthquake_info.Model.Data.Countries.GeoNames;

import java.util.List;

@Dao
public interface GeoNamesDAO {

    @Query("SELECT * FROM geonames")
    List<GeoNames> getAll();

    @Query("SELECT * FROM geonames WHERE countryName = :countryName")
    GeoNames getByName(String countryName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (List<GeoNames> countries);

    @Delete()
    void deleteAll(List<GeoNames> countries);

    @Update
    void update(List<GeoNames> countries);


}
