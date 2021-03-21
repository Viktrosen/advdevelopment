package advdevelopment.com.history.view.history

import android.os.Bundle
import androidx.lifecycle.Observer
import advdevelopment.com.core.BaseActivity
import advdevelopment.com.history.R
import advdevelopment.com.history.injectDependencies
import advdevelopment.com.history.view.history.HistoryAdapter
import advdevelopment.com.history.view.history.HistoryInteractor
import advdevelopment.com.history.view.history.HistoryViewModel
import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.userdata.DataModel
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.scope.currentScope

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    override val layoutRes = R.layout.activity_history
    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        check(history_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        injectDependencies()
        val viewModel: HistoryViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }
}
