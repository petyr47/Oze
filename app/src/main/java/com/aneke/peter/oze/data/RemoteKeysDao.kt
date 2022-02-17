package com.aneke.peter.oze.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_key_table WHERE repoId = :id")
    suspend fun remoteKeysPost(id : String) : RemoteKeys?

    @Query("DELETE FROM remote_key_table")
    suspend fun clearRemoteKeys()

}