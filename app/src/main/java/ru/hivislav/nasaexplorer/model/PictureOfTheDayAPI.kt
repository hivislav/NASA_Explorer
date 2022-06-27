package ru.hivislav.nasaexplorer.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.hivislav.nasaexplorer.model.entities.PictureOfTheDayDTO

interface PictureOfTheDayAPI {
    @GET(PICTURE_OF_THE_DAY_END_POINT)
    fun getPictureOfTheDay(@Query("api_key") apiKey:String):Call<PictureOfTheDayDTO>
}