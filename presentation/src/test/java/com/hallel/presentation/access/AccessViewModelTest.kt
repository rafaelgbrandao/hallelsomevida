package com.hallel.presentation.access

import com.hallel.domain.user.UserFormErrors
import com.hallel.domain.user.UserUseCase
import com.hallel.domain.utils.ResultWrapper
import com.hallel.presentation.infra.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

class AccessViewModelTest : BaseTest() {

    @MockK
    private lateinit var userUseCase: UserUseCase

    private val viewModel by lazy {
        AccessViewModel(userUseCase, dispatchers)
    }

    @Test
    fun whenVerifyIfUserExist_assertEmailIsMalformed() {
        every { userUseCase.isValidEmail(any()) } returns false
        testDispatcher.runBlockingTest {
            viewModel.onVerifyIfUserExist(MALFORMED_EMAIL)

            assertEquals(Unit, viewModel.showInvalidEmailMessage().value)
        }
    }

    @Test
    fun whenVerifyIfUserExist_assertEmailIsRegistered() {
        every { userUseCase.isValidEmail(any()) } returns true
        every { userUseCase.userAlreadyRegistered(any()) } returns true
        testDispatcher.runBlockingTest {
            viewModel.onVerifyIfUserExist(VALID_EMAIL)

            assertEquals(Unit, viewModel.navigateToNextScreen().value)
        }
    }

    @Test
    fun whenVerifyIfUserExist_assertEmailIsNotRegistered() {
        every { userUseCase.isValidEmail(any()) } returns true
        every { userUseCase.userAlreadyRegistered(any()) } returns false
        testDispatcher.runBlockingTest {
            viewModel.onVerifyIfUserExist(VALID_EMAIL)

            assertEquals(Unit, viewModel.userNotRegistered().value)
        }
    }

    @Test
    fun whenValidateFormFields_assertFieldsAreCorrect() {
        every { userUseCase.isValidEmail(any()) } returns true
        every { userUseCase.isValidBirthday(any()) } returns true
        every { userUseCase.isValidName(any()) } returns true
        every { userUseCase.isValidPhone(any()) } returns true

        viewModel.onValidateFormFields(
            name = VALID_NAME,
            email = VALID_EMAIL,
            birthday = VALID_BIRTHDAY,
            phone = VALID_PHONE,
            isPrivacyPolicyChecked = true
        )

        assertEquals(Unit, viewModel.registerNewUserAfterValidateFormFields().value)
    }

    @Test
    fun whenValidateFormFields_assertFieldsAreIncorrect() {
        every { userUseCase.isValidEmail(any()) } returns false
        every { userUseCase.isValidBirthday(any()) } returns false
        every { userUseCase.isValidName(any()) } returns false
        every { userUseCase.isValidPhone(any()) } returns false

        viewModel.onValidateFormFields(
            name = INVALID_NAME,
            email = MALFORMED_EMAIL,
            birthday = INVALID_BIRTHDAY,
            phone = INVALID_PHONE,
            isPrivacyPolicyChecked = false
        )

        assertEquals(5, viewModel.showFormErrors().value?.size)
        assertEquals(true, viewModel.showFormErrors().value?.contains(UserFormErrors.INVALID_NAME))
        assertEquals(true, viewModel.showFormErrors().value?.contains(UserFormErrors.INVALID_EMAIL))
        assertEquals(true, viewModel.showFormErrors().value?.contains(UserFormErrors.INVALID_PHONE))
        assertEquals(true, viewModel.showFormErrors().value?.contains(UserFormErrors.INVALID_BIRTHDAY))
        assertEquals(true, viewModel.showFormErrors().value?.contains(UserFormErrors.PRIVACY_POLICE_NOT_CHECKED))
    }

    @Test
    fun whenRegisterNewUser_thenAssertUserIsRegistered() {
        testDispatcher.runBlockingTest {
            every {
                userUseCase.registerNewUser(any(), any(), any(), any())
            } returns ResultWrapper.Success(true)

            viewModel.onRegisterNewUser(
                name = VALID_NAME,
                email = VALID_EMAIL,
                phone = VALID_PHONE,
                birthday = VALID_BIRTHDAY
            )

            assertEquals(Unit, viewModel.navigateToNextScreen().value)
        }
    }

    @Test
    fun whenRegisterNewUser_thenAssertUserIsNotRegistered() {
        testDispatcher.runBlockingTest {
            every {
                userUseCase.registerNewUser(any(), any(), any(), any())
            } returns ResultWrapper.Success(false)

            viewModel.onRegisterNewUser(
                name = VALID_NAME,
                email = VALID_EMAIL,
                phone = VALID_PHONE,
                birthday = VALID_BIRTHDAY
            )

            assertEquals(Unit, viewModel.showErrorOnRegisterNewUser().value)
        }
    }

    @Test
    fun whenRegisterNewUser_thenAssertRequestErrorWillBeHandle() {
        val exception: Exception = mockk()
        testDispatcher.runBlockingTest {
            every {
                userUseCase.registerNewUser(any(), any(), any(), any())
            } returns ResultWrapper.Error(error = exception)

            viewModel.onRegisterNewUser(
                name = VALID_NAME,
                email = VALID_EMAIL,
                phone = VALID_PHONE,
                birthday = VALID_BIRTHDAY
            )

            assertEquals(exception, viewModel.showRequestError().value)
        }
    }

    companion object {
        private const val MALFORMED_EMAIL = "domain@&%$@teste.com"
        private const val INVALID_BIRTHDAY = "12/12/2020"
        private const val INVALID_NAME = "Fulano"
        private const val INVALID_PHONE = "(00) 11111-1111"
        private const val VALID_BIRTHDAY = "12/12/2000"
        private const val VALID_NAME = "Fulano de Tal"
        private const val VALID_PHONE = "(19) 99999-9999"
        private const val VALID_EMAIL = "domain@teste.com"
    }
}