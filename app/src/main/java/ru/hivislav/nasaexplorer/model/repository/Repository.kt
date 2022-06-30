package ru.hivislav.nasaexplorer.model.repository

import retrofit2.Callback
import ru.hivislav.nasaexplorer.model.entities.PictureOfTheDayDTO


interface Repository {

    fun getPictureOfTheDayApi(callback: Callback<PictureOfTheDayDTO>)
    fun getPictureOfTheDayByDate(date: String, callback: Callback<PictureOfTheDayDTO>)

}