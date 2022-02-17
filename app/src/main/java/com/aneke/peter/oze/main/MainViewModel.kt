package com.aneke.peter.oze.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.rxjava3.cachedIn
import com.aneke.peter.oze.data.Favorite
import com.aneke.peter.oze.data.Result
import com.aneke.peter.oze.network.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx3.asFlowable
import org.reactivestreams.Subscriber
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class MainViewModel @Inject constructor(val resultRepository: ResultRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val favorites = MutableLiveData<List<Favorite>>()
    var selectedResult : Result? = null

    @ExperimentalCoroutinesApi
    fun observeResults() =
        resultRepository.observePagedData()
            .cachedIn(viewModelScope).asFlow()

    fun clearFavorites() {
        compositeDisposable.add(resultRepository.clearFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorComplete { e ->
                true
            }
            .subscribe {

            })
    }

    fun deleteFavorite(result: Result) {
        compositeDisposable.add(resultRepository.deleteFavorite(result.toFavorite())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorComplete { e ->
                e.printStackTrace()
                true
            }
            .subscribe (
                {

                }, {
                    it.printStackTrace()
                }))
    }

    fun insertFavorite(result: Result) {
        compositeDisposable.add(resultRepository.insertFavorite(result.toFavorite())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorComplete { e ->
                e.printStackTrace()
                true
            }
            .subscribe (
                {

                }, {
                    it.printStackTrace()
                }))
    }

    fun fetchFavorites() {
        compositeDisposable.add(resultRepository.fetchFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe( {
                Log.e("Fetching sub", it.toString())
                favorites.postValue(it)
            }, {
                it.printStackTrace()
            }))
    }


}