package dev.best.covidkotlin.data.repository.impl

import dev.best.covidkotlin.data.local.dao.CovidDao
import dev.best.covidkotlin.data.model.Country
import dev.best.covidkotlin.data.model.DataHalfMonthByCountry
import dev.best.covidkotlin.data.model.Global
import dev.best.covidkotlin.data.model.SumaryResponse
import dev.best.covidkotlin.data.remote.ApiService
import dev.best.covidkotlin.data.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val covidDao: CovidDao
) : UserRepository {
    override suspend fun getAllDataFromService(): SumaryResponse {
        return apiService.getAllData()
    }

    override suspend fun getDataGlobal(): Global? {
        return covidDao.getAllDataGlobal()
    }

    override suspend fun updateDB(global: Global, listCountry: List<Country>) {
        covidDao.update(listCountry, global)
    }

    override suspend fun insertListCountry(listCountry: List<Country>) {
        covidDao.insertCountry(listCountry)
    }

    override suspend fun getDataCountry(): List<Country>? {
        return covidDao.getAllDataCountry()
    }

    override suspend fun getHalfMonthByCountry(
        countryName: String,
        fromDate: String,
        toDate: String,
    ): DataHalfMonthByCountry {
        return apiService.getDataHalfMonthByCountry(
            countryName = countryName,
            fromDate = fromDate, toDate = toDate
        )
    }
}