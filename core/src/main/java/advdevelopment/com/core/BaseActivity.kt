package advdevelopment.com.core

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import advdevelopment.com.core.viewmodel.BaseViewModel
import advdevelopment.com.core.viewmodel.Interactor
import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.userdata.DataModel
import advdevelopment.com.utils.network.OnlineLiveData
import advdevelopment.com.utils.ui.AlertDialogFragment
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.loading_layout.*

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity() {
    
    protected abstract val model: BaseViewModel<T>
    protected abstract val layoutRes: Int
    protected var isNetworkAvailable: Boolean = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        subscribeToNetworkChange()
    }
    
    override fun onResume() {
        super.onResume()
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }
    
    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                                getString(R.string.dialog_tittle_sorry),
                                getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = View.VISIBLE
                    progress_bar_round.visibility = View.GONE
                    progress_bar_horizontal.progress = appState.progress!!
                } else {
                    progress_bar_horizontal.visibility = View.GONE
                    progress_bar_round.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }
    
    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
        )
    }
    
    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
                .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }
    
    private fun showViewWorking() {
        loading_frame_layout.visibility = View.GONE
    }
    
    private fun showViewLoading() {
        loading_frame_layout.visibility = View.VISIBLE
    }
    
    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }
    
    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(
                this@BaseActivity,
                Observer<Boolean> {
                    isNetworkAvailable = it
                    if (!isNetworkAvailable) {
                        Toast.makeText(
                                this@BaseActivity,
                                R.string.dialog_message_device_is_offline,
                                Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }
    
    abstract fun setDataToAdapter(data: List<DataModel>)
}
