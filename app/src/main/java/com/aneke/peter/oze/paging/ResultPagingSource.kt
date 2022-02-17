package com.aneke.peter.oze.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aneke.peter.oze.data.AppDatabase
import com.aneke.peter.oze.data.RemoteKeys
import com.aneke.peter.oze.data.Result
import com.aneke.peter.oze.network.ApiInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.rx3.awaitFirst
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class ResultPagingSource(private val service: ApiInterface, private val db: AppDatabase) :
    RemoteMediator<Int, Result>() {

    private val DEFAULT_PAGE_INDEX = 1
    private val MAX_PAGE_INDEX = 20
    private val MAX_PAGES = 20

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            if (page >= 10) delay(60000) //added due to api rate limit

            val results = service.fetchSearchResults(page = page).awaitFirst().items
            val isEndOfList = results.isEmpty() || page == MAX_PAGE_INDEX || page >= MAX_PAGES
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.resultDao().clearResultsTable()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = results.map {
                    RemoteKeys(repoId = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao().insertAll(keys)
                db.resultDao().insertResults(results)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getFirstRemoteKey(state: PagingState<Int, Result>): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { result -> db.remoteKeysDao().remoteKeysPost(result.id.toString()) }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Result>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { result -> db.remoteKeysDao().remoteKeysPost(result.id.toString()) }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, Result>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.remoteKeysDao().remoteKeysPost(repoId.toString())
            }
        }
    }


    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Result>): Any? {
        try {
            return when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getClosestRemoteKey(state)
                    remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
                }
                LoadType.APPEND -> {
                    val remoteKeys = getLastRemoteKey(state)
                        ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                    remoteKeys.nextKey
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getFirstRemoteKey(state)
                        ?: throw InvalidObjectException("Invalid state, key should not be null")
                    remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.prevKey
                }
            }
        } catch (e : Exception){
            e.printStackTrace()
            val remoteKeys = getLastRemoteKey(state)
            return remoteKeys?.nextKey ?: DEFAULT_PAGE_INDEX
        }
    }
}