package com.pavel.a692group.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.pavel.a692group.R
import com.pavel.a692group.databinding.ActivityLoginBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by p.mazhnik on 31.12.2017.
 * to 692group
 */
class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ActivityLoginBinding>(inflater,
            R.layout.activity_login, container, false)
            .apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = loginViewModel
        }

        loginViewModel.passwordError.observe(this) {
            binding.fieldPassword.error = it
        }
        loginViewModel.usernameError.observe(this) {
            binding.fieldUsername.error = it
        }

        loginViewModel.isAuthenticated.observe(this) {
            if(it) {
                activity?.finish()
            }
        }

        binding.exitButton.setOnClickListener {
            activity?.finishAffinity()
            activity?.finish()
        }
        return binding.root
    }
}

