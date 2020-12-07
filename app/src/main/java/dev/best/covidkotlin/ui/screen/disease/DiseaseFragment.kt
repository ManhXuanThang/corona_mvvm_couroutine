package dev.best.covidkotlin.ui.screen.disease

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.best.covidkotlin.databinding.FragmentDiseaseBinding
import dev.best.covidkotlin.ui.base.BaseFragment
import dev.best.covidkotlin.R

@AndroidEntryPoint
class DiseaseFragment : BaseFragment<FragmentDiseaseBinding,DiseaseViewModel>(){
    override val viewModel: DiseaseViewModel by viewModels()
    override val layoutId: Int= R.layout.fragment_disease
}