package dev.best.covidkotlin.ui.screen.home

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.best.covidkotlin.R
import dev.best.covidkotlin.databinding.FragmentHomeBinding
import dev.best.covidkotlin.ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_home

}