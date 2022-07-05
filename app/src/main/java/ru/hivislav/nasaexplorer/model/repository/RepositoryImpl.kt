package ru.hivislav.nasaexplorer.model.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.hivislav.nasaexplorer.model.NASA_API_BASE_URL
import ru.hivislav.nasaexplorer.model.NASAAPI
import ru.hivislav.nasaexplorer.model.entities.ListOfMarsPhotoDTO
import ru.hivislav.nasaexplorer.model.entities.PictureOfTheDayDTO

class RepositoryImpl: Repository {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NASA_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(NASAAPI::class.java)
    }

    override fun getPictureOfTheDayApi(callback: Callback<PictureOfTheDayDTO>) {
        retrofit.getPictureOfTheDay().enqueue(callback)
    }

    override fun getPictureOfTheDayByDate(date: String, callback: Callback<PictureOfTheDayDTO>) {
        retrofit.getPictureOfTheDayByDate(date = date).enqueue(callback)
    }

    override fun getMarsPhotosByDate(date: String, callback: Callback<ListOfMarsPhotoDTO>) {
        retrofit.getMarsPhotosByDate(date = date).enqueue(callback)
    }
}