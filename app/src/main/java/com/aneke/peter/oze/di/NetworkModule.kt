package com.aneke.peter.oze.di

import com.aneke.peter.oze.network.ApiInterface
import com.aneke.peter.oze.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideApiInterface() : ApiInterface = RetrofitClient.makeApiInterface()

}