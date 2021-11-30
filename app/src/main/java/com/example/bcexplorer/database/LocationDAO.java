package com.example.bcexplorer.database;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDAO {
    // Insert
    @Insert(onConflict = ABORT)
    void insertLocation(Location location);
    @Insert(onConflict = ABORT)
    void insertLocations(Location... locations);
    @Insert(onConflict = ABORT)
    void insertLocationList(List<Location> locationList);

    // Delete
    @Query("DELETE FROM locations")
    void deleteAllLocations();
    @Query("DELETE FROM locations WHERE locationID = :locationID")
    void deleteLocation(String locationID);

    // Retrieve
    @Query("SELECT * FROM locations")
    List<Location> getAllLocations();
    @Query("SELECT * FROM locations WHERE locationID = :locationID")
    Location getLocationWithID(String locationID);
    @Query("SELECT * FROM locations WHERE locationName = :locationName")
    Location getLocationWithName(String locationName);
    @Query("SELECT DISTINCT category FROM locations")
    List<String> getAllCategories();
    @Query("SELECT * FROM locations WHERE category = :category")
    List<Location> getLocationsMatchingCategory(String category);
    @Query("SELECT * FROM locations WHERE saved = 1 ORDER BY savedTime DESC")
    List<Location> getSavedLocations(); // Get saved locations in descending order of savedTime


    // Update
    @Query("Update locations SET saved = 1 WHERE locationID = :locationID")
    void saveLocation(String locationID);
    @Query("Update locations SET saved = 0 WHERE locationID = :locationID")
    void unsaveLocation(String locationID);
    @Query("Update locations SET savedTime = :savedTime WHERE locationID = :locationID")
    void setSavedTime(String locationID, int savedTime);
}
