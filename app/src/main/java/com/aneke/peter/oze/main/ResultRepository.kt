package com.aneke.peter.oze.main

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import androidx.paging.rxjava3.observable
import com.aneke.peter.oze.data.AppDatabase
import com.aneke.peter.oze.data.Favorite
import com.aneke.peter.oze.data.Result
import com.aneke.peter.oze.network.ApiInterface
import com.aneke.peter.oze.paging.ResultPagingSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ResultRepository @Inject constructor(val service : ApiInterface, val db : AppDatabase) {

    @ExperimentalPagingApi
    fun observePagedData() : Flowable<PagingData<Result>> {
        val config = PagingConfig(pageSize = 30)
        val pagingSourceFactory = { db.resultDao().fetchPagingResults()}

        return Pager(
            config = config,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = ResultPagingSource(service, db)
        ).flowable
    }


    fun fetchFavorites() : Flowable<List<Favorite>> {
        try {
            return db.favoriteDao().observeFavorites()
        } catch (e : Exception){
            e.printStackTrace()
            return Flowable.empty()
        }
    }


    fun clearFavorites() : Completable {
       try {
          return db.favoriteDao().clearFavorites()
       } catch (e : Exception){
           e.printStackTrace()
           return Completable.error(e)
       }
    }


    fun deleteFavorite(favorite: Favorite) : Completable {
        try {
            return db.favoriteDao().deleteFavorite(favorite)
        } catch (e : Exception){
            e.printStackTrace()
            return Completable.error(e)
        }
    }

    fun insertFavorite(favorite: Favorite) : Completable {
        try {
            return db.favoriteDao().insertFavorite(favorite)
        } catch (e : Exception){
            e.printStackTrace()
            return Completable.error(e)
        }
    }

}