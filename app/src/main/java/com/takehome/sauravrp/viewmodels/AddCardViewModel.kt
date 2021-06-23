package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.repository.FlashCardRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class AddCardViewModel(private val flashCardRepository: FlashCardRepository) : ViewModel() {

    companion object {
        const val DEFAULT_CARD_TITLE = "Default Card Title"
    }

    private val mutableViewState = MutableLiveData<ViewState>()
    private val mutableViewEvent = MutableLiveData<ViewEvent>()

    private val compositeDisposable = CompositeDisposable()

    private var cardName: String = DEFAULT_CARD_TITLE

    private val titleMap = mutableMapOf<Locale, String>()
    private val bodyMap = mutableMapOf<Locale, String>()
    private var cachedLocales : List<Locale> = emptyList()

    sealed class ViewState {
        class Error(val error: Throwable) : ViewState()
        object Loading : ViewState()
        data class Success(val locales: List<Locale>) : ViewState()
    }

    sealed class ViewEvent {
        data class SaveEnabled(val enabled: Boolean) : ViewEvent()
        object SaveCompleted : ViewEvent()
        data class SaveFailed(val error: Throwable) : ViewEvent()
    }

    val viewState: LiveData<ViewState> by lazy {
        fetchLocales()
        mutableViewState
    }

    val viewEvent: LiveData<ViewEvent> by lazy {
        isSaveEnabled()
        mutableViewEvent
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
                cachedLocales = it
                mutableViewState.value = ViewState.Success(it)
            }, {
                mutableViewState.value = ViewState.Error(it)
            })
            .also {
                compositeDisposable.add(it)
            }

    }

    fun setCardName(title: String) {
        cardName = title
        isSaveEnabled()
    }

    fun addTitle(title: String, locale: Locale) {
        titleMap[locale] = title
        isSaveEnabled()
    }

    fun addBody(body: String, locale: Locale) {
        bodyMap[locale] = body
        isSaveEnabled()
    }

    private fun isSaveEnabled() {
        var allFilled = true
        if(cachedLocales.isEmpty()) {
            allFilled = false
        }

        cachedLocales.map { locale ->
            allFilled = allFilled
                    && cardName.isNotBlank()
                    && (titleMap[locale]?.isNotBlank() ?: false)
                    && (bodyMap[locale]?.isNotBlank() ?: false)
        }

        mutableViewEvent.value = ViewEvent.SaveEnabled(allFilled)
    }

    fun save() {
        val flashCard = FlashCard(
            UUID.randomUUID().toString(),
            cardName
        )
        val contentMap = mutableMapOf<Locale, FlashContent>()
        bodyMap.keys.map { locale ->
            contentMap[locale] =
                FlashContent(
                    UUID.randomUUID().toString(),
                    titleMap[locale] ?: "",
                    bodyMap[locale] ?: ""
                )
        }

        flashCardRepository.insertFlashCard(flashCard).andThen(
            flashCardRepository.insertFlashContent(flashCard, contentMap)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableViewEvent.value = ViewEvent.SaveCompleted
            }, {
                mutableViewEvent.value = ViewEvent.SaveFailed(it)
            })
            .also {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}