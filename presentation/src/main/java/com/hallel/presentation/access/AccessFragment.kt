package com.hallel.presentation.access

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.hallel.domain.user.UserFormErrors.*
import com.hallel.domain.utils.DATE_MASK
import com.hallel.domain.utils.PHONE_MASK
import com.hallel.presentation.R
import com.hallel.presentation.extensions.*
import kotlinx.android.synthetic.main.fragment_access.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccessFragment: Fragment() {

    private val viewModel by viewModel<AccessViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_access, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initObservers()
        setSendButtonListener {
            viewModel.onVerifyIfUserExist(userEmail = accessTxtInputEditTextEmail.getInputText())
        }
    }

    private fun initFields() {
        accessTxtInputEditTextPhone.applySingleMask(PHONE_MASK) { accessTxtInputLayoutPhone.error = "" }
        accessTxtInputEditTextBirthday.applySingleMask(DATE_MASK) { accessTxtInputLayoutBirthday.error = "" }
        accessTxtInputEditTextName.setTextChangeListener(onChange = { accessTxtInputLayoutName.error = "" })
        accessTxtInputEditTextEmail.setTextChangeListener(onChange = { accessTxtInputLayoutEmail.error = "" })
    }

    private fun initObservers() {
        viewModel.userNotRegistered().observe(this) {
            enableFormViews()
            setSendButtonListener {
                viewModel.onValidateFormFields(
                    name = accessTxtInputEditTextName.getInputText(),
                    email = accessTxtInputEditTextEmail.getInputText(),
                    phone = accessTxtInputEditTextPhone.getInputText(),
                    birthday = accessTxtInputEditTextBirthday.getInputText(),
                    isPrivacyPolicyChecked = accessCheckBoxAcceptPrivacyPolicy.isChecked
                )
            }
        }

        viewModel.showFormErrors().observe(this) { errorList ->
            errorList.forEach {
                when (it) {
                    INVALID_BIRTHDAY -> accessTxtInputLayoutBirthday.error = getString(R.string.invalid_birthday)
                    INVALID_PHONE -> accessTxtInputLayoutPhone.error = getString(R.string.invalid_phone)
                    INVALID_NAME -> accessTxtInputLayoutName.error = getString(R.string.invalid_name)
                    INVALID_EMAIL -> accessTxtInputLayoutEmail.error = getString(R.string.invalid_email)
                    PRIVACY_POLICE_NOT_CHECKED -> showToast(getString(R.string.unchecked_privacy_policy_text))
                }
            }
        }

        viewModel.showInvalidEmailMessage().observe(this) {
            accessTxtInputLayoutEmail.error = getString(R.string.invalid_email)
        }

        viewModel.showErrorOnRegisterNewUser().observe(this) {
            showToast(getString(R.string.fail_on_register_new_user))
        }

        viewModel.registerNewUserAfterValidateFormFields().observe(this) {
            viewModel.registerNewUser(
                name = accessTxtInputEditTextName.getInputText(),
                email = accessTxtInputEditTextEmail.getInputText(),
                phone = accessTxtInputEditTextPhone.getInputText(),
                birthday = accessTxtInputEditTextBirthday.getInputText()
            )
        }

        viewModel.navigateToNextScreen().observe(this) {
            showToast("Will Navigate")
        }
    }

    private fun setSendButtonListener(listener: () -> Unit) {
        accessBtnSend.setOnClickListener { listener() }
    }

    private fun enableFormViews() {
        showToast(getString(R.string.user_not_registered))
        accessTxtTitle.text = getString(R.string.register_title)
        visibleViews(
            accessTxtInputLayoutName,
            accessTxtInputLayoutPhone,
            accessTxtInputLayoutBirthday,
            accessCheckBoxAcceptPrivacyPolicy
        )
    }
    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}