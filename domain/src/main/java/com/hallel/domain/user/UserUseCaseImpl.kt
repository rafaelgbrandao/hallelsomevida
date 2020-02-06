package com.hallel.domain.user

import com.hallel.data.user.User
import com.hallel.data.user.UserDao
import com.hallel.domain.extension.clearText
import com.hallel.domain.utils.*
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UserUseCaseImpl(private val userDao: UserDao) : UserUseCase {

    override fun isUserValid(): Boolean {
        return userDao.getUser() != null
    }

    override fun userAlreadyRegistered(userEmail: String): Boolean {
        return false
    }

    override fun registerNewUser(
        name: String,
        email: String,
        phone: String,
        birthday: String
    ): ResultWrapper<Boolean> {
        val newUser = User(
            userId = 1,
            userName = name,
            userEmail = email,
            userBirthday = birthday,
            userPhone = phone,
            isSent = 0
        )
        return try {
            ResultWrapper.Success(userDao.updateUser(newUser) > 0)
        } catch (exception: Exception) {
            ResultWrapper.Error(error = exception)
        }
    }

    override fun isValidBirthday(birthday: String): Boolean {
        val clearDate = birthday.clearText(DATA_CLEAR_REGEX)
        val validRange = isBirthdayRangeValid(
            date = birthday,
            allowedMinUserDate = getRangeDate(-18),
            allowedMaxUserDate = getRangeDate(-100)
        )
        return clearDate.length == 8 && validRange
    }

    override fun isValidPhone(phone: String): Boolean {
        return phone.clearText(PHONE_CLEAR_REGEX).length == 11
    }

    override fun isValidEmail(email: String): Boolean {
        return Regex(EMAIL_REGEX).matches(email)
    }

    override fun isValidName(name: String): Boolean {
        return name.isNotEmpty() && name.length >= 2
    }

    override fun getPrivacyPoliceLink(): String {
        return "https://www.google.com.br/"
    }

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
}