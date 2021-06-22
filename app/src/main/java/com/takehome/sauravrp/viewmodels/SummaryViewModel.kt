package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.repository.FlashCardRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SummaryViewModel(
        private val flashCardRepository: FlashCardRepository) : ViewModel() {

    private val mutableViewState = MutableLiveData<ViewState>()

    private val compositeDisposable = CompositeDisposable()

    val viewState: LiveData<ViewState> by lazy {
        fetchCount()
        mutableViewState
    }

    private fun fetchCount() {
        Single.zip(
                flashCardRepository.getLocaleCount(),
                flashCardRepository.getFlashCardsCount(),
                { localeCount, flashCardsCount ->
                    ViewState.Success(localeCount, flashCardsCount)

                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mutableViewState.value = ViewState.Loading
                }
                .subscribe({
                    mutableViewState.value = it
                }, {
                    mutableViewState.value = ViewState.Error(it)
                })
                .also {
                    compositeDisposable.add(it)
                }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    sealed class ViewState {
        class Error(val error: Throwable) : ViewState()
        object Loading : ViewState()
        data class Success(val localeCount: Int, val flashCardCount: Int) : ViewState()
    }
}