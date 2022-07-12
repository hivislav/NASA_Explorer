package ru.hivislav.nasaexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.hivislav.nasaexplorer.model.entities.ListOfMarsPhotoDTO
import ru.hivislav.nasaexplorer.model.repository.RepositoryImpl

class MarsViewModel(
    private val liveData: MutableLiveData<MarsAppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
):ViewModel() {

    fun getLiveData(): LiveData<MarsAppState> {
        return liveData
    }

    fun sendRequestByDate(date: String) {
        liveData.postValue(MarsAppState.Loading)
        repositoryImpl.getMarsPhotosByDate(date, callback)
    }

    private val callback = object: Callback<ListOfMarsPhotoDTO> {
        override fun onResponse(
            call: Call<ListOfMarsPhotoDTO>,
            response: Response<ListOfMarsPhotoDTO>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(MarsAppState.Success(it))
                }
            } else {
                response.body()?.let {
                    val error = Throwable(response.code().toString())
                    liveData.postValue(MarsAppState.Error(error))
                }
            }
        }

        override fun onFailure(call: Call<ListOfMarsPhotoDTO>, t: Throwable) {
            liveData.postValue(MarsAppState.Error(t))
        }
    }
}