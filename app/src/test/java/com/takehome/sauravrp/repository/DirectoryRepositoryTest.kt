package com.takehome.sauravrp.repository

import com.google.common.truth.Truth.assertThat
import com.takehome.sauravrp.TestDataHelper
import com.takehome.sauravrp.db.AppDatabase
import com.takehome.sauravrp.db.FlashCardDao
import com.takehome.sauravrp.network.WebServicesAPI
import com.takehome.sauravrp.viewmodels.FlashCard
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Test

class DirectoryRepositoryTest {

    private lateinit var flashCardRepository: FlashCardRepository

    @MockK
    lateinit var webServicesAPI: WebServicesAPI

    @MockK
    lateinit var appDatabase: AppDatabase

    @MockK
    lateinit var userDao : FlashCardDao

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        flashCardRepository = FlashCardRepository(webServicesAPI, appDatabase)
        every { appDatabase.userDao() } returns
                userDao
    }

    @Test
    fun `when services returns data, correct type is returned`() {
        val mockedObserver = TestObserver<List<FlashCard>>()

        val userDto = TestDataHelper.userDto()
        val usersDto = listOf(userDto)

        every { webServicesAPI.getUsers(1,1) } returns
                Single.just(usersDto)

        every { userDao.getAllUsers() } returns
                Single.just(listOf(userDto.toUser()))
        // when
        flashCardRepository.getUserDirectory().subscribe(mockedObserver)

       // then
        assertThat(mockedObserver.values().first().first()).isInstanceOf(FlashCard::class.java)
    }

    @Test
    fun `when services returns error, error is returned`() {
        val mockedObserver = TestObserver<List<FlashCard>>()

        val throwable = Throwable("Some Error")

        every { webServicesAPI.getUsers(1,1) } returns
                Single.error(throwable)

        every { userDao.getAllUsers() } returns
                Single.error(throwable)
        // when
        flashCardRepository.getUserDirectory().subscribe(mockedObserver)

        // then
        mockedObserver.assertError(throwable)
    }
}