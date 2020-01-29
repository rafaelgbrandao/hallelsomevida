package com.hallel.presentation.access

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hallel.domain.user.UserFormErrors
import com.hallel.domain.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccessViewModel(private val userUseCase: UserUseCase): ViewModel() {

    fun userNotRegistered(): LiveData<Unit> = lvUserNotRegistered
    private val lvUserNotRegistered = MutableLiveData<Unit>()

    fun showInvalidEmailMessage(): LiveData<Unit> = lvInvalidEmail
    private val lvInvalidEmail = MutableLiveData<Unit>()

    fun showFormErrors(): LiveData<List<UserFormErrors>> = lvFormFieldsHasError
    private val lvFormFieldsHasError = MutableLiveData<List<UserFormErrors>>()

    fun registerNewUserAfterValidateFormFields(): LiveData<Unit> = lvFormFieldsAreValid
    private val lvFormFieldsAreValid = MutableLiveData<Unit>()

    fun showErrorOnRegisterNewUser(): LiveData<Unit> = lvErrorOnRegisterNewUser
    private val lvErrorOnRegisterNewUser = MutableLiveData<Unit>()

    fun navigateToNextScreen(): LiveData<Unit> = lvNavigateToNextScreen
    private val lvNavigateToNextScreen = MutableLiveData<Unit>()

    fun onVerifyIfUserExist(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (userUseCase.isValidEmail(userEmail)) {
                when {
                    userUseCase.userAlreadyRegistered(userEmail) -> lvNavigateToNextScreen.postValue(Unit)
                    else -> lvUserNotRegistered.postValue(Unit)
                }
            } else {
                lvInvalidEmail.postValue(Unit)
            }
        }
    }

    fun onValidateFormFields(
        name: String,
        email: String,
        phone: String,
        birthday: String,
        isPrivacyPolicyChecked: Boolean
    ) {
        val errorList = mutableListOf<UserFormErrors>().apply {
            if (!userUseCase.isValidName(name)) add(UserFormErrors.INVALID_NAME)
            if (!userUseCase.isValidEmail(email)) add(UserFormErrors.INVALID_EMAIL)
            if (!userUseCase.isValidPhone(phone)) add(UserFormErrors.INVALID_PHONE)
            if (!userUseCase.isValidBirthday(birthday)) add(UserFormErrors.INVALID_BIRTHDAY)
            if (!isPrivacyPolicyChecked) add(UserFormErrors.PRIVACY_POLICE_NOT_CHECKED)
        }.toList()
        when {
            errorList.isEmpty() -> lvFormFieldsAreValid.postValue(Unit)
            else -> lvFormFieldsHasError.postValue(errorList)
        }
    }

    fun registerNewUser(name: String, email: String, phone: String, birthday: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                userUseCase.registerNewUser(name, email, phone, birthday) ->
                    lvNavigateToNextScreen.postValue(Unit)
                 else -> lvErrorOnRegisterNewUser.postValue(Unit)
            }
        }
    }
}