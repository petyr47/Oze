package com.aneke.peter.oze.data

import androidx.paging.PagingSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface ResultDao {

    @Insert
    suspend fun insertResult(result: Result)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResults(results: List<Result>)

    @Update(onConflict = REPLACE)
    fun updateResult(result: Result) : Completable

    @Query("SELECT * FROM results_table WHERE isFavorite = 1")
    fun observeFavorites() : Observable<List<Result>>

    @Query("SELECT * FROM results_table WHERE isFavorite = 1")
    suspend fun fetchFavorites() : List<Result>

    @Query("DELETE FROM results_table WHERE isFavorite = 1")
    fun clearFavorites() : Completable

    @Query("DELETE FROM results_table")
    suspend fun clearResultsTable()

    @Query("SELECT * FROM results_table")
    fun fetchPagingResults() : PagingSource<Int, Result>
}