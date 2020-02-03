package com.hallel.domain.user

import com.hallel.domain.utils.ResultWrapper

interface UserUseCase {

    fun isUserValid(): Boolean

    fun userAlreadyRegistered(userEmail: String): Boolean

    fun registerNewUser(name: String, email: String, phone: String, birthday: String): ResultWrapper<Boolean>

    fun isValidBirthday(birthday: String): Boolean

    fun isValidPhone(phone: String): Boolean

    fun isValidEmail(email: String): Boolean

    fun isValidName(name: String): Boolean
}