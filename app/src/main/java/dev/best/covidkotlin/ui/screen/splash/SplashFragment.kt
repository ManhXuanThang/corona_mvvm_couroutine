package dev.best.covidkotlin.ui.screen.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.best.covidkotlin.R
import dev.best.covidkotlin.databinding.FragmentSplashBinding
import dev.best.covidkotlin.ui.base.BaseFragment
import dev.best.covidkotlin.utils.showDialog
import dev.best.covidkotlin.utils.showLoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_splash

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            delay(2000)
            navigateToOther()
        }
    }

    private fun navigateToOther() {
        getNavController()?.navigate(
            SplashFragmentDirections.toSearch()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        context?.showLoadingDialog()
        gotoMain()
        viewModel.apply {
            this.allData.observe(viewLifecycleOwner, {
                hideLoading()
                this.updateDB(it)
               gotoMain()
            })
           this.syncDataFromService()
        }
    }

    fun gotoMain(){
        lifecycleScope.launch {
            delay(1000)
            navigateToOther()
        }
    }

}