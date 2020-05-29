package com.pavel.a692group.presentation.login

import android.text.TextUtils
import androidx.lifecycle.*
import com.pavel.a692group.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    val isAuthenticated = Transformations.map(authRepository.getCurrentUsername()) {
        it != ""
    }

    val username = MutableLiveData<String>("test")
    val password = MutableLiveData<String>("123456")

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?>
        get() = _usernameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?>
        get() = _passwordError

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun signIn() {
        if(!validateForm()) return

        viewModelScope.launch {
            authRepository.setCurrentUsername(username.value!!)
        }
    }

    private fun validateForm(): Boolean {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        return isEmailValid && isPasswordValid
    }

    private fun validateEmail(): Boolean
    {
        val value = username.value

        return if (TextUtils.isEmpty(value)) {
            _usernameError.value = "Required." //TODO : string values
            false
        }
        else
        {
            _usernameError.value = null
            true
        }
    }

    private fun validatePassword(): Boolean
    {
        val value = password.value

        return when {
            TextUtils.isEmpty(value) -> {
                _passwordError.value = "Required."
                false
            }
            value!!.length < 6 -> {
                _passwordError.value = "Use at least 6 characters"
                false
            }
            else -> {
                _passwordError.value = null
                true
            }
        }
    }
}

