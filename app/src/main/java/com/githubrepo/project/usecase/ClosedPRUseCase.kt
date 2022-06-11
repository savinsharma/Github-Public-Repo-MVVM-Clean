package com.githubrepo.project.usecase

import com.githubrepo.project.model.ClosePullRequestResponse
import com.githubrepo.project.repository.ClosedPRRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * This class will contain the API call and the data rendering.
 * This will be used as an observable which will linked with view model
 * */
class ClosedPRUseCase @Inject constructor(private val closedPRRepository: ClosedPRRepository) {

    sealed class ClosedPRResult {
        data class Success(val closedPRList: List<ClosePullRequestResponse>) : ClosedPRResult()
        data class Failure(val throwable: Throwable) : ClosedPRResult()
    }

    fun makeGetClosePRAPICall(state: String, per_page: Int): Observable<ClosedPRResult> {
        return closedPRRepository.makeGetClosePRAPICall(state, per_page)
            .toObservable().map {
                ClosedPRResult.Success(
                    it
                ) as ClosedPRResult
            }.onErrorReturn {
                ClosedPRResult.Failure(
                    it
                )
            }
    }

}