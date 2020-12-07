package dev.best.covidkotlin.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dev.best.covidkotlin.BR
import dev.best.covidkotlin.utils.dismissLLoadingDialog
import dev.best.covidkotlin.utils.showDialog
import dev.best.covidkotlin.utils.showLoadingDialog
import timber.log.Timber

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    AppCompatActivity() {

    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (::viewBinding.isInitialized.not()) {
            viewBinding = DataBindingUtil.setContentView(this, layoutId)
            viewBinding.apply {
                setVariable(BR.viewModel, viewModel)
                root.isClickable = true
                executePendingBindings()
            }
        }
        viewBinding.lifecycleOwner = this
        observeErrorEvent()
    }

    private fun observeErrorEvent() {
        viewModel.apply {
            isLoading.observe(
                this@BaseActivity, {
                    handleLoading(it == true)
                })
            errorMessage.observe(this@BaseActivity,{
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(this@BaseActivity,{
                handleErrorMessage("No Internet Connection")
            })
            connectTimeoutEvent.observe(this@BaseActivity,{
                handleErrorMessage("Connect Timeout")
            })
            forceUpdateAppEvent.observe(this@BaseActivity,{
                handleErrorMessage("Please update app")
            })
            serverMaintainEvent.observe(this@BaseActivity,{
                handleErrorMessage("Server maintain, please try again later")
            })
            unknowErrorEvent.observe(this@BaseActivity,{
                handleErrorMessage("Something error, please try again later")
            })
        }
    }

    //override this if not use loading dialog
    open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else dismissLLoadingDialog()
    }

    fun showLoading() {
        showLoadingDialog()
    }

    fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLLoadingDialog()
        Timber.e("error message activity: $message")
//        showDialog(
//            message = message,
//            textPositive = "OK"
//        )
    }
}