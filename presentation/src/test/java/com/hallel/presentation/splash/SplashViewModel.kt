package com.hallel.presentation.splash

import com.hallel.domain.update.UpdateUseCase
import com.hallel.domain.user.UserUseCase
import com.hallel.presentation.infra.BaseTest
import com.hallel.presentation.utils.CustomDispatchers
import io.mockk.BuildConfig
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SplashViewModelTest : BaseTest() {

    @MockK
    private lateinit var userUseCase: UserUseCase

    @MockK
    private lateinit var updateUserCase: UpdateUseCase

    private val dispatchers = CustomDispatchers(
        io = testDispatcher,
        main = testDispatcher
    )

    private val viewModel by lazy {
        SplashViewModel(
            userUseCase = userUseCase,
            updateUseCase = updateUserCase,
            dispatchers = dispatchers
        )
    }

    @Test
    fun whenCheckForAppUpdate_ThenShowUpdateDialog() {
        testDispatcher.runBlockingTest {
            coEvery {
                updateUserCase.onSearchForAppVersion()
            } returns BuildConfig.VERSION_CODE + 1

            viewModel.onAppSuggestedVersionCheck(BuildConfig.VERSION_CODE)

            assertEquals(Unit, viewModel.showAppUpdateDialog().value)

        }
    }

    @Test
    fun whenCheckForAppUpdate_ThenAssertAppIsUpToDate() {
        testDispatcher.runBlockingTest {
            every {
                updateUserCase.onSearchForAppVersion()
            } returns -1

            viewModel.onAppSuggestedVersionCheck(BuildConfig.VERSION_CODE)

            assertEquals(Unit, viewModel.appUpToDate().value)
        }
    }

    @Test
    fun whenValidateUser_ThenNavigateToNextScreen() {
        testDispatcher.runBlockingTest {
            every { userUseCase.isUserValid() } returns true

            viewModel.onValidateUser()

            assertEquals(true, viewModel.navigateToNextScreen().value)
        }
    }

    @FlowPreview
    @Test
    fun whenSearchForUpdate_ThenUpdateProgressBar() {
        testDispatcher.runBlockingTest {

            every {
                updateUserCase.onSearchForContentUpdates(testDispatcher)
            } returns flow { emit(Pair(1, 2)) }

            viewModel.onSearchForUpdate()

            assertEquals(Unit, viewModel.contentUpdateVerificationStarted().value)
            assertEquals(true, viewModel.updateContentProgressBar().value != null)
            assertEquals(1, viewModel.updateContentProgressBar().value?.first)
            assertEquals(2, viewModel.updateContentProgressBar().value?.second)
            assertEquals(Unit, viewModel.contentUpdateVerificationFinished().value)
        }
    }
}