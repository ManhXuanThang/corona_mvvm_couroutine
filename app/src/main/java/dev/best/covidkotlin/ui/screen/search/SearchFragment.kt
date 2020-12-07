package dev.best.covidkotlin.ui.screen.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.best.covidkotlin.R
import dev.best.covidkotlin.databinding.FragmentSearchBinding
import dev.best.covidkotlin.ui.base.BaseFragment
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_search

    private val listSearchAdapter = ListSearchAdapter(
        itemClickListener = { country ->
            country.let {

            }

        }
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.recyclerview.adapter = listSearchAdapter
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner, Observer {

            })

            this.allData.observe(viewLifecycleOwner, {
                this.updateDB(it)
            })

            this.dataByListCountry.observe(viewLifecycleOwner, {
                listSearchAdapter.submitList(it)
            })

            this.dataByGlobal.observe(viewLifecycleOwner, {
                Timber.e("dataGlobal: " + it.TotalConfirmed)
            })

            newListCountry.observe(viewLifecycleOwner,{
                listSearchAdapter.submitList(it)
            })

            viewBinding.editText.doAfterTextChanged {
                lifecycleScope.launch {
                    searchNewCountry(it.toString())
                }
            }
            this.getAllData()
            this.getAllDataByCountry()
            this.getAllDataByGlobal()

        }
    }
}