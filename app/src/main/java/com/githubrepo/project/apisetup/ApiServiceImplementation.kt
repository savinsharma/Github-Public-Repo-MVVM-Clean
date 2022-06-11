package com.githubrepo.project.apisetup

import com.githubrepo.project.model.ClosePullRequestResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * This class will interact with repository and will have all the API func which then link with APIServices.kt
 * */
class ApiServiceImplementation @Inject constructor(private val apiInterface: ApiServices) {

    fun getClosedPRData(state: String, per_page: Int): Single<List<ClosePullRequestResponse>> {
        return apiInterface.getUserClosedPR(state, per_page)
    }
}