package com.pavel.a692group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.pavel.a692group.presentation.login.LoginViewModel
import com.pavel.a692group.room.AppDatabase
import com.pavel.a692group.room.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Created by p.mazhnik on 17.11.2017.
 * to 692group
 */
class `InfoActivity.java` : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel()

    val context: Context = this
    private var ConstraintLayout: ConstraintLayout? = null
    private var SendButton: Button? = null
    private var EditDbButton: Button? = null
    private var EditText: EditText? = null
    private var mDatabase: AppDatabase? = null
    private var mDisposable: Disposable? = null
    var group_str: ArrayList<String>? = null
    private var group_list: ArrayList<CheckBox>? = null
    private var CheckBox_All: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        ConstraintLayout = findViewById(R.id.info_container)
        val actionBar = supportActionBar
        actionBar!!.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        mDatabase = AppDatabase.createPersistentDatabase(this)
        group_str = ArrayList()
        mDisposable = mDatabase
            .getUserDao()
            .allGroup
            .observeOn(AndroidSchedulers.mainThread()) //Чтобы результат пришел в UI поток, используем observeOn.
            .subscribe(object :
                Consumer<List<String>?> {
                @Throws(Exception::class)
                override fun accept(groaps: List<String>?) {
                    // Log.d("Inf", "size: " + groaps.get(0).mGroup);
                    group_str!!.addAll(groaps!!) ///TODO: исправить проблему с необновленим списка групп после изменений в других активити
                    CheckBoxCreate() //cоздаем чекбоксы на основе списка групп
                }
            }, object : Consumer<Throwable?> {
                @Throws(Exception::class)
                override fun accept(throwable: Throwable?) {
                    Log.e("Inf", "accept: ", throwable)
                }
            })
        EditText = findViewById(R.id.editText)
        EditText.setText(context.getString(R.string.hello_text))
        SendButton =
            findViewById(R.id.send_button) //TODO: обработать ошибку отправки при невыборе списка
        SendButton.setOnClickListener(View.OnClickListener {
            //TODO: добавить вызов окошка с предупреждением об отправке
        })
        EditDbButton = findViewById(R.id.info_edit_button)
        EditDbButton.setOnClickListener(View.OnClickListener { startEditDbActivity() })
    }

    override fun onStop() {
        super.onStop()
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
    }

    private fun CheckBoxCreate() {
        CheckBox_All = findViewById(R.id.checkBoxAll)
        CheckBox_All.setText(User.TABLE)

        //layout params for every CheckBox
        val AllCheckBoxParams =
            CheckBox_All.getLayoutParams() as ConstraintLayout.LayoutParams
        if (!group_str!!.isEmpty()) { //открытие бд, получение списка групп и создание в активити чекбоксов с этими группами.
            var index = 0
            var id = R.id.checkBoxAll
            group_list = ArrayList()
            while (index != group_str!!.size) {
                val Box = CheckBox(this)
                Box.text = group_str!![index]
                Box.id = index + 10
                val Params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                    androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                Params.leftMargin = AllCheckBoxParams.leftMargin
                Params.topMargin = AllCheckBoxParams.topMargin
                Params.startToStart = AllCheckBoxParams.startToStart
                Params.topToTop = AllCheckBoxParams.topToTop
                Params.verticalBias = AllCheckBoxParams.verticalBias
                Params.bottomToTop = id
                id = Box.id
                ConstraintLayout!!.addView(Box, Params)
                group_list!!.add(Box)
                index++
            }
        }
        CheckBox_All.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                for (i in group_list!!.indices) {
                    group_list!![i].isChecked = true
                }
            } else {
                for (i in group_list!!.indices) {
                    group_list!![i].isChecked = false
                }
            }
        })
    }

    private fun startEditDbActivity() {
        startActivity(Intent(this, EditDbActivity::class.java))
    }
}