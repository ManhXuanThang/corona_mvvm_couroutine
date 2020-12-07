package dev.best.covidkotlin.ui.screen.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.best.covidkotlin.data.model.Country
import dev.best.covidkotlin.data.model.Global
import dev.best.covidkotlin.data.model.SumaryResponse
import dev.best.covidkotlin.data.repository.UserRepository
import dev.best.covidkotlin.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class SearchViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val allData = MutableLiveData<SumaryResponse>()
    val dataByGlobal = MutableLiveData<Global>()
    val dataByListCountry = MutableLiveData<List<Country>>()
    val newListCountry = MutableLiveData<List<Country>>()

    //load data
    fun getAllData() {
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
//                userRepository.insertListCountry(response.Countries)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun getAllDataByGlobal() {
        viewModelScope.launch {
            try {
                dataByGlobal.value = userRepository.getDataGlobal()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }


    fun getAllDataByCountry() {
        viewModelScope.launch {
            try {
                dataByListCountry.value = userRepository.getDataCountry()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    suspend fun searchNewCountry(query: String) {
        withContext(Dispatchers.Main) {
            newListCountry.value =
                dataByListCountry.value?.filter {
                    it.Country.contains(query, ignoreCase = true)
                }
        }
    }
}