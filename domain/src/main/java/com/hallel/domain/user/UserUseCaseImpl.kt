package com.hallel.domain.user

import com.hallel.data.user.UserDao

class UserUseCaseImpl(private val userDao: UserDao): UserUseCase {

    override fun isUserValid(): Boolean {
        return userDao.getUser() != null
    }
}