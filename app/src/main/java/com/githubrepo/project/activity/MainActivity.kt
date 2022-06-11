package com.githubrepo.project.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.githubrep.project.R
import com.githubrepo.project.viewmodel.ClosedPRViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val closedPRViewModel: ClosedPRViewModel by viewModels()
    private val per_page = 10
    private val state = "closed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getClosedPRListAPI()
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


    override fun onDestroy() {
        super.onDestroy()
        closedPRViewModel.unbound()
        closedPRViewModel.closedPRListResponse.removeObservers(this)
        closedPRViewModel.showErrorGettingData.removeObservers(this)
    }
}