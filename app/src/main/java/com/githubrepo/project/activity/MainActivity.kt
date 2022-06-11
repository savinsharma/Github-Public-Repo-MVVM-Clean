package com.githubrepo.project.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubrep.project.R
import com.githubrep.project.databinding.ActivityMainBinding
import com.githubrepo.project.adapter.ClosedPRListAdapter
import com.githubrepo.project.viewmodel.ClosedPRViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val closedPRViewModel: ClosedPRViewModel by viewModels()
    private lateinit var activityMainBinding : ActivityMainBinding
    private var closedPRListAdapter : ClosedPRListAdapter? = null
    private val per_page = 10
    private val state = "closed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        getClosedPRListAPI()
    }

    private fun initViews(){
        activityMainBinding.rvClosedPR.layoutManager = LinearLayoutManager(this)
    }

    private fun getClosedPRListAPI() {
        closedPRViewModel.boundClosedPRListAPI(state, per_page)
        closedPRViewModel.closedPRListResponse.observe(this) {
            println("API - Success - $it")
        }
        closedPRViewModel.showErrorGettingData.observe(this) {
            println("APi - Error - ${it.message} ")
        }
    }

    private fun setOrUpdateAdapter() {
        if(closedPRListAdapter == null){

        }else{

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closedPRViewModel.unbound()
        closedPRViewModel.closedPRListResponse.removeObservers(this)
        closedPRViewModel.showErrorGettingData.removeObservers(this)
    }
}