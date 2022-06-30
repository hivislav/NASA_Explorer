package ru.hivislav.nasaexplorer.viewmodel

import ru.hivislav.nasaexplorer.model.entities.PictureOfTheDayDTO

sealed class AppState {
    data class Success(val pictureOfTheDayResponseDTO: PictureOfTheDayDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}