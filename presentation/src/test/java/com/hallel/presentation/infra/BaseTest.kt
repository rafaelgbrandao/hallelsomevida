package com.hallel.presentation.infra

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hallel.presentation.utils.CustomDispatchers
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    val dispatchers = CustomDispatchers(
        io = testDispatcher,
        main = testDispatcher
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun onFinish() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}