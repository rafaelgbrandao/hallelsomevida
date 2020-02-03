package com.hallel.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hallel.domain.update.UpdateUseCase
import com.hallel.domain.user.UserUseCase
import com.hallel.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(
    private val updateUseCase: UpdateUseCase,
    private val userUseCase: UserUseCase
): BaseViewModel() {

    fun showAppUpdateDialog(): LiveData<Unit> = lvLastVersionNumber
    private val lvLastVersionNumber = MutableLiveData<Unit>()

    fun appUpToDate(): LiveData<Unit> = lvAppUpToDate
    private val lvAppUpToDate = MutableLiveData<Unit>()

    fun noUpdateFound(): LiveData<Unit> = lvUpdateNotFound
    private val lvUpdateNotFound = MutableLiveData<Unit>()

    fun updateContentProgressBar(): LiveData<Pair<Int, Int>> = lvContentUpdateProgress
    private val lvContentUpdateProgress = MutableLiveData<Pair<Int, Int>>()

    fun hasContentForUpdate(): LiveData<Boolean> = lvShowUpdateProgressBar
    private val lvShowUpdateProgressBar = MutableLiveData<Boolean>()

    fun navigateToNextScreen(): LiveData<Boolean> = lvNavigateToNextScreen
    private val lvNavigateToNextScreen = MutableLiveData<Boolean>()

    @FlowPreview
    fun onSearchForUpdate() {
        viewModelScope.launch {
            lvShowUpdateProgressBar.postValue(true)
            updateUseCase.onSearchForContentUpdates().collect {
                lvContentUpdateProgress.postValue(it)
            }
            lvShowUpdateProgressBar.postValue(false)
        }
    }

    fun onAppSuggestedVersionCheck(versionCode: Int) {
        when {
            versionCode < updateUseCase.onSearchForAppVersion() ->
                lvLastVersionNumber.postValue(Unit)
            else -> lvAppUpToDate.postValue(Unit)
        }
    }

    fun onValidateUser() =
        viewModelScope.launch(Dispatchers.IO) {
            lvNavigateToNextScreen.postValue(userUseCase.isUserValid())
        }
}