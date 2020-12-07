package dev.best.covidkotlin.ui.screen.sumarychart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.best.covidkotlin.R
import dev.best.covidkotlin.data.model.ItemMenu
import dev.best.covidkotlin.databinding.FragmentSumaryChartBinding
import dev.best.covidkotlin.ui.base.BaseFragment
import dev.best.covidkotlin.utils.DateTimeUtils

@AndroidEntryPoint
class SumaryChartFragment : BaseFragment<FragmentSumaryChartBinding, SumaryChartViewModel>() {
    override val viewModel: SumaryChartViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_sumary_chart

    val listDemo = listOf<ItemMenu>(
        ItemMenu("Overview", false),
        ItemMenu("Select Country", false),
        ItemMenu("Growth Rate", false),
        ItemMenu("Total Amount", false),
        ItemMenu("Last Three Mouth", false)
    )

    private val listMenuAdapter = MenuAdapter(
        itemClickListener = { itemMenu ->
            itemMenu.let {
//                when(){
//                    it. -> Timber.e("")
//                }
            }

        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerview.adapter = listMenuAdapter
        listMenuAdapter.submitList(listDemo)

        viewModel.apply {
            val fromDate =
                DateTimeUtils.formatDate(DateTimeUtils.yyyyMMddTHHmmssZ, viewModel.getDaysAgo(21))
            val toDate =
                DateTimeUtils.formatDate(DateTimeUtils.yyyyMMddTHHmmssZ, viewModel.getDaysAgo(1))
            this.getDataGlobalFromDB()
            this.getPrivious20Day("vietnam", fromDate = fromDate, toDate = toDate)


            this.dataByDay.observe(viewLifecycleOwner, {
                viewBinding.AAChartView.aa_drawChartWithChartModel(
                    viewModel.configureGradientColorAreasplineChart(
                        it
                    )
                )
            })
        }
    }

}