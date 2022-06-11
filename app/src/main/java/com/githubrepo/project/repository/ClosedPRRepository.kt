package com.githubrepo.project.repository

import com.githubrepo.project.apisetup.ApiServiceImplementation
import com.githubrepo.project.model.ClosePullRequestResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Repository class that has all the API and data layer function linked with API Implementation
 * which will interact with use case class.
 */
@Suppress("UNCHECKED_CAST")
class ClosedPRRepository @Inject constructor(
    private val apiServiceImplementation: ApiServiceImplementation) {

    fun makeGetClosePRAPICall(state: String, per_page: Int) : Single<List<ClosePullRequestResponse>> {
        return apiServiceImplementation.getClosedPRData(state, per_page)
    }

}