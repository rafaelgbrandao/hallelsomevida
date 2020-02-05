package com.hallel.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hallel.domain.update.UpdateUseCase
import com.hallel.domain.user.UserUseCase
import com.hallel.presentation.base.BaseViewModel
import com.hallel.presentation.utils.CustomDispatchers
import com.hallel.presentation.utils.Event
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(
    private val updateUseCase: UpdateUseCase,
    private val userUseCase: UserUseCase,
    private val dispatchers: CustomDispatchers
): BaseViewModel() {

    fun showAppUpdateDialog(): LiveData<Unit> = lvLastVersionNumber
    private val lvLastVersionNumber = MutableLiveData<Unit>()

    fun appUpToDate(): LiveData<Unit> = lvAppUpToDate
    private val lvAppUpToDate = MutableLiveData<Unit>()

    fun contentUpdateVerificationFinished(): LiveData<Unit> = lvFinishUpdateContentVerification
    private val lvFinishUpdateContentVerification = MutableLiveData<Unit>()

    fun updateContentProgressBar(): LiveData<Pair<Int, Int>> = lvContentUpdateProgress
    private val lvContentUpdateProgress = MutableLiveData<Pair<Int, Int>>()

    fun contentUpdateVerificationStarted(): LiveData<Unit> = lvStartContentUpdateVerification
    private val lvStartContentUpdateVerification = MutableLiveData<Unit>()

    fun navigateToNextScreen(): LiveData<Event<Boolean>> = lvNavigateToNextScreen
    private val lvNavigateToNextScreen = MutableLiveData<Event<Boolean>>()

    @FlowPreview
    fun onSearchForUpdate() {
        viewModelScope.launch(dispatchers.main) {
            lvStartContentUpdateVerification.postValue(Unit)
            updateUseCase.onSearchForContentUpdates(dispatchers.io).collect {
                lvContentUpdateProgress.postValue(it)
            }
            lvFinishUpdateContentVerification.postValue(Unit)
        }
    }

    fun onAppSuggestedVersionCheck(versionCode: Int) {
        viewModelScope.launch(dispatchers.io) {
            val appVersion = updateUseCase.onSearchForAppVersion()
            when {
                versionCode < appVersion -> lvLastVersionNumber.postValue(Unit)
                else -> lvAppUpToDate.postValue(Unit)
            }
        }
    }

    fun onValidateUser() {
        viewModelScope.launch(dispatchers.io) {
            lvNavigateToNextScreen.postValue(Event(userUseCase.isUserValid()))
        }
    }
}