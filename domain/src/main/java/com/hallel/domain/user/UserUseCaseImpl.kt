package com.hallel.domain.user

import com.hallel.data.user.User
import com.hallel.data.user.UserDao
import com.hallel.domain.extension.clearText
import com.hallel.domain.utils.DATA_CLEAR_REGEX
import com.hallel.domain.utils.EMAIL_REGEX
import com.hallel.domain.utils.PHONE_CLEAR_REGEX
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UserUseCaseImpl(private val userDao: UserDao): UserUseCase {

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
    ): Boolean {
        val newUser = User(
            userId = 1,
            userName = name,
            userEmail = email,
            userBirthday = birthday,
            userPhone = phone,
            isSent = 0
        )
        return userDao.updateUser(newUser) > 0
    }

    override fun isValidBirthday(birthday: String): Boolean {
        return birthday.clearText(DATA_CLEAR_REGEX).length == 8 && isBirthdayRangeValid(birthday)
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

    internal fun isBirthdayRangeValid(
        date: String,
        datePattern: String = "dd/MM/yyyy",
        pattern: SimpleDateFormat = SimpleDateFormat(datePattern, Locale("pt", "BR")),
        calendar: Calendar = Calendar.getInstance()
    ): Boolean {
        return try {
            val allowedMinUserDate = calendar.apply { add(Calendar.YEAR, -18) }
            val allowedMaxUserDate = calendar.apply { add(Calendar.YEAR, -100) }
            pattern.isLenient = false
            val dateFormat = pattern.parse(date)
            dateFormat?.let {
                it.before(allowedMinUserDate.time) && it.after(allowedMaxUserDate.time)
            } ?: false
        } catch (e: ParseException) {
            false
        }
    }
}