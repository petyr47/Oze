package com.aneke.peter.oze.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key_table")
data class RemoteKeys(
    @PrimaryKey
    val repoId : String,
    val prevKey : Int?,
    val nextKey : Int?
)
