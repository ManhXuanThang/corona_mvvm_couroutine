package dev.best.covidkotlin.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dev.best.covidkotlin.BR
import dev.best.covidkotlin.utils.dismissLLoadingDialog
import dev.best.covidkotlin.utils.showDialog
import dev.best.covidkotlin.utils.showLoadingDialog
import timber.log.Timber

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {
    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (::viewBinding.isInitialized.not()) {
            viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            viewBinding.apply {
                setVariable(BR.viewModel, viewModel)
                root.isClickable = true
                executePendingBindings()
            }
        }
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner, Observer {
                handleLoading(it == true)
            })
            errorMessage.observe(viewLifecycleOwner, Observer {
                handleErrorMessage("No internet Connection")
            })
            connectTimeoutEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage("Connect Timeout")
            })
            forceUpdateAppEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage("Please update app")
            })
            serverMaintainEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage("Server maintain,please try again later")
            })
            unknowErrorEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage("Something error, please try again later")
            })
        }
    }

    open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else dismissLLoadingDialog()
    }

    fun showLoading() {
        context?.showLoadingDialog()
    }

    fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLLoadingDialog()
        Timber.e("error message fragment: $message")
        context?.showDialog(
            message = message,
            textPositive = "OK"
        )
    }

    fun Fragment.getNavController(): NavController? {
        return try {
            NavHostFragment.findNavController(this)
        } catch (e: IllegalAccessException) {
            null
        }
    }

}