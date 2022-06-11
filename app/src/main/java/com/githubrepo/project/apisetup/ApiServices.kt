package com.githubrepo.project.apisetup

import com.githubrepo.project.constant.NetworkConstants
import com.githubrepo.project.model.ClosePullRequestResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL


/**
 * This file will contain all the APIs that are required in the Application
 * */

interface ApiServices {

    @GET(NetworkConstants.USER_NAME + NetworkConstants.REPO_NAME + "pulls/")
    fun getUserClosedPR(
        @Query(value = "state") state: String,
        @Query("per_page") per_page: Int
    ): Single<List<ClosePullRequestResponse>>
}