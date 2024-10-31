package com.r.cohen.garbagetripscalculator.repos

import com.r.cohen.garbagetripscalculator.models.BagWeightsRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val endpoint = "garbage-trips-calculator-api.onrender.com"

// singleton repository which calls the api
object GarbageTripsCalculatorAPIRepo {
    private val apiService = Retrofit.Builder()
        .baseUrl("https://$endpoint")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GarbageTripsCalculatorAPIService::class.java)

    suspend fun calculateMinTripsCount(bagWeights: List<Float>): Int {
        val response = apiService.calculateMinTrips(BagWeightsRequest(bagWeights))
        return response.minNumberOfTrips
    }
}