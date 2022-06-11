package com.githubrepo.project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubrepo.project.extensions.addTo
import com.githubrepo.project.model.ClosePullRequestResponse
import com.githubrepo.project.usecase.ClosedPRUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ClosedPRViewModel @Inject constructor(private val closedPRUseCase: ClosedPRUseCase): ViewModel() {
    private val disposables = CompositeDisposable()
    val closedPRListResponse = MutableLiveData<List<ClosePullRequestResponse>>()
    val showErrorGettingData = MutableLiveData<Error>()

    /**
     * Method to initiate userList api flow and wait for the response using subscriber and observer
     *
     */
    fun boundClosedPRListAPI(state: String, per_page: Int, page: Int) {
        closedPRUseCase.makeGetClosePRAPICall(state, per_page, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleResultOfClosedPRAPI(it) }
            .addTo(disposables)
    }

    private fun handleResultOfClosedPRAPI(result: ClosedPRUseCase.ClosedPRResult) = when (result) {
        is ClosedPRUseCase.ClosedPRResult.Success -> {
            closedPRListResponse.value = result.closedPRList
        }
        is ClosedPRUseCase.ClosedPRResult.Failure -> {
            val error = Error()
            showErrorGettingData.value = error
        }
    }

    fun unbound() {
        disposables.clear()
    }
}