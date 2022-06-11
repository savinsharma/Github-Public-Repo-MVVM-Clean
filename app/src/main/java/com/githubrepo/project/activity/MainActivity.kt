package com.githubrepo.project.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubrep.project.R
import com.githubrep.project.databinding.ActivityMainBinding
import com.githubrepo.project.adapter.ClosedPRListAdapter
import com.githubrepo.project.model.ClosePullRequestResponse
import com.githubrepo.project.viewmodel.ClosedPRViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val closedPRViewModel: ClosedPRViewModel by viewModels()
    private lateinit var activityMainBinding: ActivityMainBinding
    private var closedPRListAdapter: ClosedPRListAdapter? = null
    private val per_page = 10
    private val state = "closed"
    private var page = 1
    private var lastpageCalled = false
    private var closedPRListMain : ArrayList<ClosePullRequestResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        getClosedPRListAPI()
    }

    private fun initViews() {
        activityMainBinding.rvClosedPR.layoutManager = LinearLayoutManager(this)
    }

    private fun getClosedPRListAPI() {
        activityMainBinding.progressBar.isIndeterminate = true
        activityMainBinding.progressBar.visibility = View.VISIBLE

        closedPRViewModel.boundClosedPRListAPI(state, per_page, page)

        closedPRViewModel.closedPRListResponse.observe(this) {
            if (!it.isNullOrEmpty()) {

                val previousIndex = closedPRListMain.size
                lastpageCalled = false
                closedPRListMain.addAll(it)
                val newIndex = closedPRListMain.size

                setOrUpdateAdapter(closedPRListMain, previousIndex, newIndex)
            } else {
                lastpageCalled = true
            }
        }
        closedPRViewModel.showErrorGettingData.observe(this) {
            println("API - Error - $it ")
            activityMainBinding.progressBar.visibility = View.GONE
            lastpageCalled = true
        }
    }

    private fun setOrUpdateAdapter(closedPRList: ArrayList<ClosePullRequestResponse>, previousIndex: Int, newIndex : Int) {
        // Setting up Adapter
        if (closedPRListAdapter == null) {
            closedPRListAdapter = ClosedPRListAdapter(closedPRList)
            activityMainBinding.rvClosedPR.adapter = closedPRListAdapter
        } else {
            // Notify the adapter for pagination
            closedPRListAdapter?.notifyItemRangeChanged(previousIndex, newIndex)
        }
        page++
        activityMainBinding.progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        closedPRViewModel.unbound()
        closedPRViewModel.closedPRListResponse.removeObservers(this)
        closedPRViewModel.showErrorGettingData.removeObservers(this)
    }
}