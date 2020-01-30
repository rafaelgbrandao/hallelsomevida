package com.hallel.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hallel.presentation.BuildConfig
import com.hallel.presentation.R
import com.hallel.presentation.base.BaseFragment
import com.hallel.presentation.extensions.gone
import com.hallel.presentation.extensions.goneViews
import com.hallel.presentation.extensions.observe
import com.hallel.presentation.extensions.visible
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.ext.android.inject

class SplashFragment: BaseFragment() {

    private val viewModel by inject<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarVisible(false)
        startObservers()
        viewModel.onAppSuggestedVersionCheck(BuildConfig.VERSION_CODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun startObservers() {

        viewModel.showAppUpdateDialog().observe(this) {
            showAlertDialog(
                title = getString(R.string.dialog_update_title),
                message = getString(R.string.dialog_update_message),
                positiveButtonText = getString(R.string.dialog_update_positive_button_text),
                positiveButtonListener = { showToast("Positive Click") },
                negativeButtonText = getString(R.string.dialog_update_negative_button_text),
                negativeButtonListener = { searchForUpdates() }
            )
        }

        viewModel.appUpToDate().observe(this)  {
            searchForUpdates()
        }

        viewModel.noUpdateFound().observe(this) {
            validateUser()
        }

        viewModel.hasContentForUpdate().observe(this) { showProgress ->
            when {
                showProgress -> {
                    splashProgressBarContentSearch.gone()
                    splashProgressBarContentUpdate.visible()
                    splashTextViewUpdateProgress.text = getString(R.string.progress_update_text)
                }
                else -> validateUser()
            }
        }

        viewModel.updateContentProgressBar().observe(this) { (currentProgress, maxValue) ->
            splashProgressBarContentUpdate.max = maxValue
            splashProgressBarContentUpdate.progress = currentProgress
        }

        viewModel.navigateToNextScreen().observe(this) { hasUser ->
            /*val nextScreen = when {
                hasUser -> SplashFragment::class.java
                else -> SplashFragment::class.java
            }
            startActivity(Intent(context, nextScreen))*/
            showToast("Next Screen")
        }
    }

    private fun searchForUpdates() {
        viewModel.onSearchForUpdate()
    }

    private fun validateUser() {
        goneViews(
            splashProgressBarContentSearch,
            splashProgressBarContentUpdate,
            splashTextViewUpdateProgress
        )
        viewModel.onValidateUser()
    }
}