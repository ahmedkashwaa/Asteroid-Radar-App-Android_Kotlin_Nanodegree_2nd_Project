package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface AsteroidApiService {

    @GET(Constants.HTTP_GET_NEO_FEED_PATH)
    suspend fun getAsteroids(
        @Query(Constants.QUERY_START_DATE_PARAM) startDate: String,
        @Query(Constants.QUERY_END_DATE_PARAM) endDate: String,
        @Query(Constants.QUERY_API_KEY_PARAM) apiKey: String): String

    @GET(Constants.HTTP_GET_APOD_PATH)
    suspend fun getPictureOfDay(@Query(Constants.QUERY_API_KEY_PARAM) apiKey: String) : PictureOfDay
}

object AsteroidApi {



    private val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }

    suspend fun getAsteroids() : List<Asteroid> {
        val responseStr = retrofitService.getAsteroids("","", Constants.API_KEY)
        val responseJsonObject = JSONObject(responseStr)

        return parseAsteroidsJsonResult(responseJsonObject)
    }

    suspend fun getPictureOfDay() = retrofitService.getPictureOfDay(Constants.API_KEY)
}