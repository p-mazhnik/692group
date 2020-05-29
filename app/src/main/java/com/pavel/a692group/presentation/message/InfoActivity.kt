package com.pavel.a692group.presentation.message

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.pavel.a692group.R
import com.pavel.a692group.databinding.ActivityInfoBinding
import com.pavel.a692group.presentation.login.AuthActivity
import com.pavel.a692group.presentation.login.LoginViewModel
import com.pavel.a692group.presentation.users.EditDbActivity
import com.pavel.a692group.data.entity.User
import kotlinx.android.synthetic.main.activity_info.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Created by p.mazhnik on 17.11.2017.
 * to 692group
 */
class InfoActivity: AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel()
    private val messageViewModel: MessageViewModel by viewModel()

    private lateinit var binding: ActivityInfoBinding

    private val groupList: ArrayList<CheckBox> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        loginViewModel.isAuthenticated.observe(this) {
            if(!it) {
                startLoginActivity()
            }
        }

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_info
        )
        binding.lifecycleOwner = this
        binding.messageViewModel = messageViewModel
        binding.loginViewModel = loginViewModel

        val actionBar = supportActionBar
        actionBar!!.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        messageViewModel.groups.observe(this){
            checkBoxCreate(it)
        }

        sendButton.setOnClickListener{
            //TODO: добавить вызов окошка с предупреждением об отправке
        }

        info_edit_button.setOnClickListener{ startEditDbActivity() }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent)
    }

    private fun checkBoxCreate(group_str: List<String?>?) {

        checkBoxAll?.text = User.TABLE

        //layout params for every CheckBox
        val allCheckBoxParams =
            checkBoxAll?.layoutParams as ConstraintLayout.LayoutParams
        if (group_str!!.isNotEmpty()) { //создание в активити чекбоксов с этими группами.
            var index = 0
            var id = R.id.checkBoxAll
            while (index != group_str.size) {
                val checkBox = CheckBox(this)
                checkBox.text = group_str[index]
                checkBox.id = index + 10
                val layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.leftMargin = allCheckBoxParams.leftMargin
                layoutParams.topMargin = allCheckBoxParams.topMargin
                layoutParams.startToStart = allCheckBoxParams.startToStart
                layoutParams.topToTop = allCheckBoxParams.topToTop
                layoutParams.verticalBias = allCheckBoxParams.verticalBias
                layoutParams.bottomToTop = id
                id = checkBox.id
                info_container!!.addView(checkBox, layoutParams)
                groupList.add(checkBox)
                index++
            }
        }
        checkBoxAll?.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                for (i in groupList.indices) {
                    groupList[i].isChecked = true
                }
            } else {
                for (i in groupList.indices) {
                    groupList[i].isChecked = false
                }
            }
        }
    }

    private fun startEditDbActivity() {
        startActivity(Intent(this, EditDbActivity::class.java))
    }
}

