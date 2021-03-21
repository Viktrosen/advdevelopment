package advdevelopment.com.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import advdevelopment.com.core.BaseActivity
//import advdevelopment.com.history.view.history.HistoryActivity
import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.userdata.DataModel
import advdevelopment.com.translator.R
import advdevelopment.com.translator.di.injectDependencies
import advdevelopment.com.translator.utils.convertMeaningsToSingleString
import advdevelopment.com.translator.view.descriptionscreen.DescriptionActivity
import advdevelopment.com.translator.view.main.adapter.MainAdapter
import advdevelopment.com.utils.ui.viewById
import android.annotation.SuppressLint
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import org.koin.android.scope.currentScope

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
private const val HISTORY_ACTIVITY_PATH = "advdevelopment.com.history.view.history.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
private const val REQUEST_CODE = 42

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    
    override val layoutRes = R.layout.activity_main
    override lateinit var model: MainViewModel
    
    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)
    
    private lateinit var splitInstallManager: SplitInstallManager
    private lateinit var appUpdateManager: AppUpdateManager
    
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val fabClickListener: View.OnClickListener =
            View.OnClickListener {
                val searchDialogFragment = SearchDialogFragment.newInstance()
                searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
                searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
            }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
            object : MainAdapter.OnListItemClickListener {
                override fun onItemClick(data: DataModel) {
                    startActivity(
                            DescriptionActivity.getIntent(
                                    this@MainActivity,
                                    data.text,
                                    convertMeaningsToSingleString(data.meanings),
                                    data.meanings[0].imageUrl
                            )
                    )
                }
            }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
            object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    if (isNetworkAvailable) {
                        model.getData(searchWord, isNetworkAvailable)
                    } else {
                        showNoInternetConnectionDialog()
                    }
                }
            }
    private val stateUpdatedListener: InstallStateUpdatedListener =
            InstallStateUpdatedListener { state ->
                state?.let {
                    if (it.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate()
                    }
                }
            }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iniViewModel()
        initViews()
        checkForUpdates()
    }
    
    override fun onResume() {
        super.onResume()
        appUpdateManager
                .appUpdateInfo
                .addOnSuccessListener { appUpdateInfo ->
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate()
                    }
                    if (appUpdateInfo.updateAvailability()
                            == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                    ) {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                IMMEDIATE,
                                this,
                                REQUEST_CODE
                        )
                    }
                }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(
                        applicationContext,
                        "Update flow failed! Result code: $resultCode",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        menu?.findItem(R.id.menu_screen_settings)?.isVisible =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        return super.onCreateOptionsMenu(menu)
    }
    
    @SuppressLint("InlinedApi")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request =
                        SplitInstallRequest
                                .newBuilder()
                                .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                                .build()
                
                splitInstallManager
                        .startInstall(request)
                        .addOnSuccessListener {
                            val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                    applicationContext,
                                    "Couldn't download feature: " + it.message,
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                true
            }
            R.id.menu_screen_settings -> {
                startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), 42)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                        appUpdateIntent,
                        IMMEDIATE,
                        this,
                        REQUEST_CODE
                )
            }
        }
    }
    
    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
                findViewById(R.id.activity_main_layout),
                getString(R.string.snackbar_update_downloaded_notification),
                Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }
    
    private fun iniViewModel() {
        check(mainActivityRecyclerView.adapter == null) { "The ViewModel should be initialised first" }
        injectDependencies()
        val viewModel: MainViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }
    
    private fun initViews() {
        searchFAB.setOnClickListener(fabClickListener)
        mainActivityRecyclerView.adapter = adapter
    }
}
