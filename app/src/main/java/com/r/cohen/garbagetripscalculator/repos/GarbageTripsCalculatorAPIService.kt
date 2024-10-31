package com.r.cohen.garbagetripscalculator.repos

import com.r.cohen.garbagetripscalculator.models.BagWeightsRequest
import com.r.cohen.garbagetripscalculator.models.TripsResponse
import retrofit2.http.Body
import retrofit2.http.POST

// retrofit api service interface
interface GarbageTripsCalculatorAPIService {

    // POST request
    @POST("calculate_min_trips")
    suspend fun calculateMinTrips(
        @Body request: BagWeightsRequest
    ): TripsResponse
}