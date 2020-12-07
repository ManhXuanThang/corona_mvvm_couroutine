package dev.best.covidkotlin.ui.screen.countrydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.best.covidkotlin.databinding.FragmentDiseaseBinding
import dev.best.covidkotlin.ui.base.BaseFragment
import dev.best.covidkotlin.R
import dev.best.covidkotlin.databinding.FragmentDetailCountryBinding
import dev.best.covidkotlin.utils.DateTimeUtils
import timber.log.Timber

@AndroidEntryPoint
class DetailCountryFragment : BaseFragment<FragmentDetailCountryBinding,DetailCountryViewModel>(){
    override val viewModel: DetailCountryViewModel by viewModels()
    override val layoutId: Int= R.layout.fragment_detail_country


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            val fromDate =
                DateTimeUtils.formatDate(DateTimeUtils.yyyyMMddTHHmmssZ, viewModel.getDaysAgo(4380))
            val toDate =
                DateTimeUtils.formatDate(DateTimeUtils.yyyyMMddTHHmmssZ, viewModel.getDaysAgo(1))
            this.getDataGlobalFromDB()
            this.getPrivious20Day("vietnam", fromDate = fromDate, toDate = toDate)


            this.dataByDay.observe(viewLifecycleOwner, {
                viewBinding.aaChartView.aa_drawChartWithChartModel(
                    viewModel.configureGradientColorAreasplineChart(
                        it
                    )
                )
            })
        }
    }

}