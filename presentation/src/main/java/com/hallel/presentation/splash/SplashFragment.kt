package com.hallel.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hallel.presentation.BuildConfig
import com.hallel.presentation.R
import com.hallel.presentation.extensions.gone
import com.hallel.presentation.extensions.goneViews
import com.hallel.presentation.extensions.observe
import com.hallel.presentation.extensions.visible
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.ext.android.inject

class SplashFragment: Fragment() {

    private val viewModel by inject<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            Toast.makeText(context, "Show Update", Toast.LENGTH_LONG).show()
            /*showSimpleDialog(
                title = "Nova versão disponível",
                message = "Deseja atualizar?",
                positiveButtonText = "Sim",
                positiveButtonListener = { Toast.makeText(context, "Positive Click", Toast.LENGTH_LONG).show() },
                negativeButtonText = "Não",
                negativeButtonListener = { searchForUpdates() }
            )*/
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
                    splashTextViewUpdateProgress.text = "Atualizando"
                }
                else -> validateUser()
            }
        }

        viewModel.updateContentProgressBar().observe(this) { (currentProgress, maxValue) ->
            splashProgressBarContentUpdate.max = maxValue
            splashProgressBarContentUpdate.progress = currentProgress
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
        Toast.makeText(context, "Validate User", Toast.LENGTH_LONG).show()
        //viewModel.onValidateUser()
    }
}