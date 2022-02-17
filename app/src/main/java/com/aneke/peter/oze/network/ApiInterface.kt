package com.aneke.peter.oze.network

import com.aneke.peter.oze.network.models.LocationSearchResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    fun fetchSearchResults(
        @Query("q") query : String = "lagos",
        @Query("page") page : Int
    ) : Observable<LocationSearchResponse>

}