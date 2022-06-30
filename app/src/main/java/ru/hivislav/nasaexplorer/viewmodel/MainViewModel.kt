package ru.hivislav.nasaexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.hivislav.nasaexplorer.BuildConfig
import ru.hivislav.nasaexplorer.model.entities.PictureOfTheDayDTO
import ru.hivislav.nasaexplorer.model.repository.RepositoryImpl

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
):ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun sendRequest() {
        liveData.postValue(AppState.Loading)
        repositoryImpl.getPictureOfTheDayApi(callback)
    }

    fun sendRequestByDate(date: String) {
        liveData.postValue(AppState.Loading)
        repositoryImpl.getPictureOfTheDayByDate(date, callback)
    }

    private val callback = object: Callback<PictureOfTheDayDTO> {
        override fun onResponse(
            call: Call<PictureOfTheDayDTO>,
            response: Response<PictureOfTheDayDTO>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(AppState.Success(it))
                }
            } else {
                response.body()?.let {
                    val error = Throwable(response.code().toString())
                    liveData.postValue(AppState.Error(error))
                }
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
            liveData.postValue(AppState.Error(t))
        }
    }
}