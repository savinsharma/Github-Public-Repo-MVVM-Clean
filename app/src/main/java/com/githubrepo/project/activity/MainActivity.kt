package com.githubrepo.project.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var closedPRListMain: ArrayList<ClosePullRequestResponse> = arrayListOf()
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        observeListData()
        getClosedPRListAPI(page)
    }

    private fun initViews() {
        linearLayoutManager = LinearLayoutManager(this)
        activityMainBinding.rvClosedPR.layoutManager = linearLayoutManager
        activityMainBinding.rvClosedPR.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visibleItemCount = linearLayoutManager?.childCount ?: 0
                val totalItemCount = linearLayoutManager?.itemCount ?: 0
                val firstVisibileItem = linearLayoutManager?.findFirstVisibleItemPosition() ?: 0

                if ((visibleItemCount + firstVisibileItem) >= totalItemCount) {
                    if (!lastpageCalled) {
                        page++
                        getClosedPRListAPI(page)
                    }
                }
            }
        })
    }

    private fun getClosedPRListAPI(page : Int) {
        activityMainBinding.progressBar.isIndeterminate = true
        activityMainBinding.progressBar.visibility = View.VISIBLE
        closedPRViewModel.boundClosedPRListAPI(state, per_page, page)
    }

    private fun observeListData(){
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

    private fun setOrUpdateAdapter(
        closedPRList: ArrayList<ClosePullRequestResponse>,
        previousIndex: Int,
        newIndex: Int
    ) {
        // Setting up Adapter
        if (closedPRListAdapter == null) {
            closedPRListAdapter = ClosedPRListAdapter(this, closedPRList,
                onLastPositionReached ={
                    // we can use this to show loading indicator at the bottom
                })
            activityMainBinding.rvClosedPR.adapter = closedPRListAdapter
        } else {
            // Notify the adapter for pagination
            closedPRListAdapter?.notifyItemRangeChanged(previousIndex, newIndex)
            //closedPRListAdapter?.notifyDataSetChanged()
        }
        activityMainBinding.progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        closedPRViewModel.unbound()
        closedPRViewModel.closedPRListResponse.removeObservers(this)
        closedPRViewModel.showErrorGettingData.removeObservers(this)
    }
}