package ru.hivislav.nasaexplorer.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.hivislav.nasaexplorer.BuildConfig
import ru.hivislav.nasaexplorer.model.entities.PictureOfTheDayDTO

interface PictureOfTheDayAPI {
    @GET(PICTURE_OF_THE_DAY_END_POINT)
    fun getPictureOfTheDay(@Query("api_key") apiKey:String = BuildConfig.NASA_API_KEY):Call<PictureOfTheDayDTO>

    @GET(PICTURE_OF_THE_DAY_END_POINT)
    fun getPictureOfTheDayByDate(@Query("api_key") apiKey:String = BuildConfig.NASA_API_KEY, @Query("date") date:String):Call<PictureOfTheDayDTO>
}