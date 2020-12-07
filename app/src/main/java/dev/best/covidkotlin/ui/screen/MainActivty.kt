package dev.best.covidkotlin.ui.screen

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.best.covidkotlin.R
import dev.best.covidkotlin.databinding.MainActivityBinding
import dev.best.covidkotlin.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivty : BaseActivity<MainActivityBinding, MainActivityViewModel>() {
    override val viewModel: MainActivityViewModel by viewModels()
    override val layoutId: Int = R.layout.main_activity
}