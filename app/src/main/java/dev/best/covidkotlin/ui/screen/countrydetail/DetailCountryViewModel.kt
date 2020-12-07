package dev.best.covidkotlin.ui.screen.countrydetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import dev.best.covidkotlin.data.local.pref.PrefHelper
import dev.best.covidkotlin.data.model.DataHalfMonthByCountry
import dev.best.covidkotlin.data.repository.UserRepository
import dev.best.covidkotlin.ui.screen.sumarychart.SumaryChartViewModel
import dev.best.covidkotlin.utils.libs.aainfographics.aachartcreator.*
import dev.best.covidkotlin.utils.libs.aainfographics.aatools.AAGradientColor
import dev.best.covidkotlin.utils.libs.aainfographics.aatools.AALinearGradientDirection

class DetailCountryViewModel @ViewModelInject constructor(
    private val userRepositorys: UserRepository,
    private val prefHelpers: PrefHelper
) : SumaryChartViewModel(userRepository = userRepositorys, prefHelper = prefHelpers) {

    val isYear = MutableLiveData<Boolean>()
    val isMonth = MutableLiveData<Boolean>()
    val isWeek = MutableLiveData<Boolean>()
    val isDay = MutableLiveData<Boolean>()


    fun setEventClick(
        isYearValue: Boolean,
        isMonthValue: Boolean,
        isWeekValue: Boolean,
        isDayValue: Boolean
    ) {
        isYear.value = isYearValue
        isMonth.value = isMonthValue
        isWeek.value = isWeekValue
        isDay.value = isDayValue
    }
}