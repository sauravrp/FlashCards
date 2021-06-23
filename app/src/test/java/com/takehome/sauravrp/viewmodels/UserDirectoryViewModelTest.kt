package com.takehome.sauravrp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.takehome.sauravrp.repository.FlashCardRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins.reset
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins.setInitMainThreadSchedulerHandler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class UserDirectoryViewModelTest {

    private lateinit var viewModel: FlashCardViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var flashCardRepository: FlashCardRepository

    @Before
    fun setup() {
        /**
         * https://stackoverflow.com/questions/46549405/testing-asynchronous-rxjava-code-android
         */
        reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockKAnnotations.init(this)
        viewModel = FlashCardViewModel(flashCardRepository)
    }

    @After
    fun tearDown() {
        reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun `when fetch users called, then loading return true`() {
        val mockedObserver = createUsersObsserver()

        // https://stackoverflow.com/questions/48980897/unit-testing-rxjava-doonsubscribe-and-dofinally
        val data = emptyList<FlashCard>()
        val delayer = PublishSubject.create<Boolean>()
        every { (flashCardRepository.getUserDirectory()) } returns
                Single.just(data).delaySubscription(delayer)

        // when
        viewModel.viewState.observeForever(mockedObserver)

        // then
        val slot = slot<FlashCardViewModel.ViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(FlashCardViewModel.ViewState.Loading)
    }


    @Test
    fun `when fetch users called is successful, then view statue return success state`() {
        val mockedObserver = createUsersObsserver()

        val data = emptyList<FlashCard>()
        every { (flashCardRepository.getUserDirectory()) } returns
                Single.just(data)

        // when
        viewModel.viewState.observeForever(mockedObserver)

        // then
        val slot = slot<FlashCardViewModel.ViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isInstanceOf(FlashCardViewModel.ViewState.FlashCards::class.java)
        assertThat((slot.captured as FlashCardViewModel.ViewState.FlashCards).data)
            .isEqualTo(data)
    }

    @Test
    fun `when fetch users called failed, then view statue return error state`() {
        val mockedObserver = createUsersObsserver()

        val error = Throwable("Some Error")
        every { (flashCardRepository.getUserDirectory()) } returns
                Single.error(error)

        // when
        viewModel.viewState.observeForever(mockedObserver)

        // then
        val slot = slot<FlashCardViewModel.ViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isInstanceOf(FlashCardViewModel.ViewState.Error::class.java)
        assertThat((slot.captured as FlashCardViewModel.ViewState.Error).error)
            .isEqualTo(error)
    }

    private fun createUsersObsserver(): Observer<FlashCardViewModel.ViewState> =
        spyk(Observer { })
}