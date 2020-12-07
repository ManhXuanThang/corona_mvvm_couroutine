package dev.best.covidkotlin.ui.screen.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.best.covidkotlin.data.model.SumaryResponse
import dev.best.covidkotlin.data.repository.UserRepository
import dev.best.covidkotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    val allData = MutableLiveData<SumaryResponse>()

    //load data
    fun syncDataFromService() {
//        showLoading()
        viewModelScope.launch {
            try {
                allData.value = userRepository.getAllDataFromService()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun updateDB(response: SumaryResponse) {
        viewModelScope.launch {
            try {
                userRepository.updateDB(response.Global, response.Countries)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}