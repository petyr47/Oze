package com.aneke.peter.oze.di

import android.content.Context
import com.aneke.peter.oze.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase = AppDatabase.getInstance(appContext)

}