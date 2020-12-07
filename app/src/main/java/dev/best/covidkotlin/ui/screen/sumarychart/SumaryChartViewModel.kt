package dev.best.covidkotlin.ui.screen.sumarychart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.best.covidkotlin.data.local.pref.PrefHelper
import dev.best.covidkotlin.data.model.DataHalfMonthByCountry
import dev.best.covidkotlin.data.model.Global
import dev.best.covidkotlin.data.model.ItemMenu
import dev.best.covidkotlin.data.repository.UserRepository
import dev.best.covidkotlin.ui.base.BaseViewModel
import dev.best.covidkotlin.utils.libs.aainfographics.aachartcreator.*
import dev.best.covidkotlin.utils.libs.aainfographics.aatools.AAGradientColor
import dev.best.covidkotlin.utils.libs.aainfographics.aatools.AALinearGradientDirection
import kotlinx.coroutines.launch
import java.util.*


open class SumaryChartViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    val prefHelper: PrefHelper
) : BaseViewModel() {
    val isClickDrawer = MutableLiveData<Boolean>()
    val dataGlobal = MutableLiveData<Global>()
    var dataByDay = MutableLiveData<DataHalfMonthByCountry>()

    fun handleClickDrawer() {
        isClickDrawer.value = isClickDrawer.value != true
    }


    fun configureGradientColorAreasplineChart(dataByDay:DataHalfMonthByCountry): AAChartModel {
        val stopsArr: Array<Any> = arrayOf(
            arrayOf(0, "rgba(2255,20,147,1)"), //深粉色, alpha 透明度 1
            arrayOf(1, "rgba(255,105,180,0.1)")//热情的粉红, alpha 透明度 0.1
        ) //颜色字符串设置支持十六进制类型和 rgba 类型

        val linearGradientColor = AAGradientColor.linearGradient(
            AALinearGradientDirection.ToBottom,
            stopsArr
        )

        return AAChartModel()
            .chartType(AAChartType.Areaspline)
            .title("")
            .subtitle("")
            .categories(
                arrayOf(
                    "T1",
                    "T2",
                    "T3",
                    "T4",
                    "T5",
                    "T6",
                    "T7",
                    "T8",
                    "T9",
                    "T10",
                    "T11",
                    "T12",
                    "T13",
                    "T14",
                    "T15"
                )
            )
            .yAxisTitle("")
            .markerRadius(8f)//marker点半径为8个像素
            .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)//marker点为空心效果
            .markerSymbol(AAChartSymbolType.Circle)//marker点为圆形点○
            .yAxisLineWidth(0f)
            .yAxisGridLineWidth(0f)
            .legendEnabled(false)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Covid Status")
                        .lineWidth(5.0f)
                        .color("rgba(220,20,60,1)")//猩红色, alpha 透明度 1
                        .fillColor(linearGradientColor)
                        .data(
                            arrayOf(
                                dataByDay[0].Active,
                                dataByDay[1].Active,
                                dataByDay[2].Active,
                                dataByDay[3].Active,
                                dataByDay[4].Active,
                                dataByDay[5].Active,
                                dataByDay[6].Active,
                                dataByDay[7].Active,
                                dataByDay[8].Active,
                                dataByDay[9].Active,
                                dataByDay[10].Active,
                                dataByDay[11].Active,
                                dataByDay[12].Active,
                                dataByDay[13].Active,
                                dataByDay[14].Active
                            )
                        )
                )
            )
    }

    fun getDataGlobalFromDB() {
        viewModelScope.launch {
            dataGlobal.value = userRepository.getDataGlobal()
        }
    }

    fun getPrivious20Day(
        countryName: String,
        fromDate: String,
        toDate: String
    ) {
        viewModelScope.launch {
            try {
                dataByDay.value= userRepository.getHalfMonthByCountry(
                    countryName = countryName,
                    fromDate = fromDate,
                    toDate = toDate
                )
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }


}