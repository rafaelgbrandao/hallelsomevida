package com.hallel.presentation.access

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hallel.domain.user.UserFormErrors.*
import com.hallel.domain.utils.DATE_MASK
import com.hallel.domain.utils.PHONE_MASK
import com.hallel.presentation.R
import com.hallel.presentation.base.BaseFragment
import com.hallel.presentation.extensions.*
import kotlinx.android.synthetic.main.fragment_access.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccessFragment: BaseFragment() {

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
        setActionBarVisible(true)
        setActionBarTitle(getString(R.string.access_txt_access_title))
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
                    INVALID_BIRTHDAY -> accessTxtInputLayoutBirthday.error = getString(R.string.access_txt_invalid_birthday)
                    INVALID_PHONE -> accessTxtInputLayoutPhone.error = getString(R.string.access_txt_invalid_phone)
                    INVALID_NAME -> accessTxtInputLayoutName.error = getString(R.string.access_txt_invalid_name)
                    INVALID_EMAIL -> accessTxtInputLayoutEmail.error = getString(R.string.access_txt_invalid_email)
                    PRIVACY_POLICE_NOT_CHECKED -> showToast(getString(R.string.access_txt_unchecked_privacy_policy_text))
                }
            }
        }

        viewModel.showInvalidEmailMessage().observe(this) {
            accessTxtInputLayoutEmail.error = getString(R.string.access_txt_invalid_email)
        }

        viewModel.showErrorOnRegisterNewUser().observe(this) {
            showToast(getString(R.string.access_txt_fail_on_register_new_user))
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
        setActionBarTitle(getString(R.string.access_txt_register_title))
        accessTxtSubtitle.text = getString(R.string.access_txt_user_not_registered)
        accessBtnSend.text = getString(R.string.access_btn_register)
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