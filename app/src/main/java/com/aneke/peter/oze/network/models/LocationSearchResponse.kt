package com.aneke.peter.oze.network.models

import com.aneke.peter.oze.data.Result

data class LocationSearchResponse(
    val incomplete_results: Boolean = false,
    val items: List<Result> = listOf(),
    val total_count: Int = 0
)