package com.hallel.domain.user

import com.hallel.data.user.User
import com.hallel.data.user.UserDao
import com.hallel.domain.utils.DATE_PATTERN_LITTLE_ENDIAN
import com.hallel.domain.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UserUseCaseTest {

    @MockK
    private lateinit var userDao: UserDao

    private val userUseCase by lazy {
        UserUseCaseImpl(userDao)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun whenVerifyIfUserIsValid_thenReturnUserIsValid() {
        every { userDao.getUser() } returns user

        val isUserValid = userUseCase.isUserValid()

        assertEquals(true, isUserValid)
    }

    @Test
    fun whenVerifyIfUserIsValid_thenReturnUserIsInvalid() {
        every { userDao.getUser() } returns null

        val isUserValid = userUseCase.isUserValid()

        assertEquals(false, isUserValid)
    }

    /* Not implemented it*/
    @Test
    fun whenVerifyIfUserAlreadyRegistered_thenReturnUserIsRegistered() {
        val userIsRegistered = userUseCase.userAlreadyRegistered(EMAIL)
        assertEquals(false, userIsRegistered)
    }

    @Test
    fun whenRegisterNewUser_thenAssertUserIsRegistered() {
        every { userDao.updateUser(any()) } returns 1

        val result = userUseCase.registerNewUser(
            name = NAME,
            email = EMAIL,
            birthday = BIRTHDAY,
            phone = PHONE
        )

        assertEquals(true, (result as ResultWrapper.Success).value)
    }

    @Test
    fun whenRegisterNewUser_thenAssertUserIsNotRegistered() {
        every { userDao.updateUser(any()) } returns 0
        val result = userUseCase.registerNewUser(
            name = NAME,
            email = EMAIL,
            birthday = BIRTHDAY,
            phone = PHONE
        )

        assertEquals(false, (result as ResultWrapper.Success).value)
    }

    @Test
    fun whenRegisterNewUser_thenAssertFailWhenTryToRegister() {
        val exception: Exception = mockk()
        every { userDao.updateUser(any()) } throws exception

        val result = userUseCase.registerNewUser(
            name = NAME,
            email = EMAIL,
            birthday = BIRTHDAY,
            phone = PHONE
        )

        assertEquals(exception, (result as ResultWrapper.Error).error)
    }

    @Test
    fun whenValidatePhone_thenAssertPhoneIsValid() {
        val isValidPhone = userUseCase.isValidPhone(PHONE)

        assertEquals(true, isValidPhone)
    }

    @Test
    fun whenValidatePhone_thenAssertPhoneIsInvalid() {
        val isValidPhone = userUseCase.isValidPhone(INVALID_PHONE)

        assertEquals(true, isValidPhone)
    }

    @Test
    fun whenValidateName_thenAssertNameIsValid() {
        val isValidName = userUseCase.isValidName(NAME)

        assertEquals(true, isValidName)
    }

    @Test
    fun whenValidateName_thenAssertNameIsInvalid() {
        val isValidName = userUseCase.isValidName(INVALID_NAME)

        assertEquals(false, isValidName)
    }

    @Test
    fun whenValidateEmail_thenAssertEmailIsValid() {
        val isValidEmail = userUseCase.isValidEmail(EMAIL)

        assertEquals(true, isValidEmail)
    }

    @Test
    fun whenValidateBirthday_thenAssertBirthdayIsInvalid() {
        val isValidBirthday = userUseCase.isValidBirthday(INVALID_BIRTHDAY)

        assertEquals(false, isValidBirthday)
    }

    @Test
    fun whenValidateBirthday_thenAssertBirthdayIsValid() {
        val isValidBirthday = userUseCase.isValidBirthday(BIRTHDAY)

        assertEquals(true, isValidBirthday)
    }

    @Test
    fun whenValidateEmail_thenAssertEmailIsInvalid() {
        val isValidEmail = userUseCase.isValidEmail(INVALID_EMAIL)

        assertEquals(false, isValidEmail)
    }

    @Test
    fun whenIsBirthdayRangeValid_thenBirthdayOnValidRange() {

        val isValidBirthdayRange = userUseCase.isBirthdayRangeValid(
            date = BIRTHDAY,
            allowedMaxUserDate = userUseCase.getRangeDate(MAX_RANGE),
            allowedMinUserDate = userUseCase.getRangeDate(MIN_RANGE)
        )

        assertEquals(true, isValidBirthdayRange)
    }

    @Test
    fun whenIsBirthdayRangeValid_thenBirthdayOnInvalidRange() {

        val isValidBirthdayRange = userUseCase.isBirthdayRangeValid(
            date = BIRTHDAY,
            allowedMaxUserDate = userUseCase.getRangeDate(MIN_RANGE),
            allowedMinUserDate = userUseCase.getRangeDate(MAX_RANGE)
        )

        assertEquals(false, isValidBirthdayRange)
    }

    companion object {
        private const val EMAIL = "teste@teste.com"
        private const val NAME = "Fulano de tal"
        private const val PHONE = "(19) 99999-9999"
        private const val BIRTHDAY = "12/12/2000"
        private const val INVALID_EMAIL = "teste@teste"
        private const val INVALID_NAME = "Z"
        private const val INVALID_PHONE = "(19) 11111-1111"
        private const val INVALID_BIRTHDAY = "12/12/2020"
        private const val MAX_RANGE = -100
        private const val MIN_RANGE = -18
        private val user = User(
            isSent = 0,
            userName = NAME,
            userEmail = EMAIL,
            userBirthday = BIRTHDAY,
            userPhone = PHONE,
            userId = 1
        )
    }

    /*

    internal fun isBirthdayRangeValid(
        date: String,
        datePattern: String = DATE_PATTERN_LITTLE_ENDIAN,
        pattern: SimpleDateFormat = SimpleDateFormat(datePattern, Locale("pt", "BR")),
        allowedMinUserDate: Calendar,
        allowedMaxUserDate: Calendar
    ): Boolean {
        return try {
            pattern.isLenient = false
            pattern.parse(date)?.let {
                val before = it.before(allowedMinUserDate.time)
                val after = it.after(allowedMaxUserDate.time)
                before && after
            } ?: false
        } catch (e: ParseException) {
            false
        }
    }

    internal fun getRangeDate(range: Int, calendar: Calendar = Calendar.getInstance()) =
        calendar.apply { add(Calendar.YEAR, range) }

     */
}