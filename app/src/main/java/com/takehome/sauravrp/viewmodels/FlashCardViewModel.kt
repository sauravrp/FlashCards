package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.repository.FlashCardRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FlashCardViewModel(
        private val flashCardRepository: FlashCardRepository
) : ViewModel() {

    sealed class ViewState {
        class Error(val error: Throwable) : ViewState()
        object Loading : ViewState()
        class Success(val data: List<FlashCard>) : ViewState()
    }

    sealed class ViewEvent {
        data class ShowDetails(val data: FlashCard) : ViewEvent()
    }

    private val mutableViewEvent = MutableLiveData<ViewEvent>()
    val viewEvent: LiveData<ViewEvent> by lazy {
        mutableViewEvent
    }

    private val mutableViewState = MutableLiveData<ViewState>()

    val viewState: LiveData<ViewState> by lazy {
        mutableViewState
    }

    private var disposable: Disposable? = null

    fun fetchFlashCards() {
        disposable?.dispose()
        flashCardRepository
                .getFlashCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mutableViewState.value = ViewState.Loading
                }
                .subscribe({ result ->
                    mutableViewState.value = ViewState.Success(data = result)
                }, { error ->
                    mutableViewState.value = ViewState.Error(error = error)
                }).also {
                    disposable = it
                }
    }

    fun setSelection(card: FlashCard) {
        mutableViewEvent.value = ViewEvent.ShowDetails(card)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

