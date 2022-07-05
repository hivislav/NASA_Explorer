package ru.hivislav.nasaexplorer.viewmodel

import ru.hivislav.nasaexplorer.model.entities.ListOfMarsPhotoDTO


sealed class MarsAppState {
    data class Success(val listOfMarsPhotos: ListOfMarsPhotoDTO) : MarsAppState()
    data class Error(val error: Throwable) : MarsAppState()
    object Loading : MarsAppState()
}