package com.pavel.a692group.presentation.users

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pavel.a692group.R
import com.pavel.a692group.databinding.ActivityEditUserBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by p.mazhnik on 01.01.2018.
 * to 692group
 */
class EditUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mUserId: Long = intent.extras?.getLong(ID_KEY, 0) ?: 0
        Log.d("EditUserActivity", "EditUserActivity started with user_id: $mUserId")
        val detailViewModel: UserDetailViewModel by viewModel{ parametersOf(mUserId)}

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_edit_user
        )
        binding.detailViewModel = detailViewModel
        binding.lifecycleOwner = this
        detailViewModel.init()


        val actionBar = supportActionBar
        actionBar!!.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
    }

    companion object {
        var ID_KEY = "ID_KEY"
    }
}

