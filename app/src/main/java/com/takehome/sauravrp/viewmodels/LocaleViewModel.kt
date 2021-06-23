package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.repository.FlashCardRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class LocaleViewModel(private val flashCardRepository: FlashCardRepository) : ViewModel() {

    private val mutableViewState = MutableLiveData<ViewState>()

    private val compositeDisposable = CompositeDisposable()

    val viewState: LiveData<ViewState> by lazy {
        fetchLocales()
        mutableViewState
    }

    private fun fetchLocales() {
                flashCardRepository
                        .getLocales()
                        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mutableViewState.value = ViewState.Loading
                }
                .subscribe({
                    mutableViewState.value = ViewState.Success(it)
                }, {
                    mutableViewState.value = ViewState.Error(it)
                })
                .also {
                    compositeDisposable.add(it)
                }

    }

    fun addLocale(locale: String) {
        val newLocale = Locale(UUID.randomUUID().toString(), locale)
        flashCardRepository.addLocale(newLocale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           fetchLocales()
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
        data class Success(val locales: List<Locale>) : ViewState()
    }
}