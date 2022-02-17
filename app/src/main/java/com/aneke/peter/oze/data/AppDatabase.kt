package com.aneke.peter.oze.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Result::class, Favorite::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun remoteKeysDao() : RemoteKeysDao
    abstract fun resultDao() : ResultDao
    abstract fun favoriteDao() : FavoritesDao

    companion object{
        private const val DB_NAME = "app_db"
        private lateinit var appDatabase : AppDatabase

        fun getInstance(context : Context) : AppDatabase {
            if (!::appDatabase.isInitialized) {
                synchronized(AppDatabase::class) {
                    appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                        .addCallback(object :RoomDatabase.Callback(){

                        })
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return appDatabase
        }
    }

}