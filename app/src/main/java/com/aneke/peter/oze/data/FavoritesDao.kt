package com.aneke.peter.oze.data

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface FavoritesDao {

    @Insert(onConflict = ABORT)
    fun insertFavorite(favorite: Favorite) : Completable

    @Delete
    fun deleteFavorite(favorite: Favorite) : Completable

    @Query("SELECT * FROM favorites_table")
    fun observeFavorites() : Flowable<List<Favorite>>

    @Query("DELETE FROM favorites_table")
    fun clearFavorites() : Completable

}