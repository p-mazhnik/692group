package com.pavel.a692group.presentation.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.observe
import com.pavel.a692group.R
import com.pavel.a692group.databinding.ActivityEditDbBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by p.mazhnik on 31.12.2017. 23:50
 * to 692group
 */
class EditDbActivity : AppCompatActivity() {

    private val usersViewModel: UsersViewModel by viewModel()

    private lateinit var binding: ActivityEditDbBinding
    private lateinit var mUserAdapter: UserAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_edit_db
        )
        binding.usersViewModel = usersViewModel
        binding.lifecycleOwner = this

        val actionBar = supportActionBar
        actionBar!!.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        /*
        Для того, чтобы мы могли заполнить наш GridView созданым объектом, надо задать адаптер.
        */
        mUserAdapter = UserAdapter(
            this,
            usersViewModel
        )
        layoutManager = LinearLayoutManager(this)

        binding.usersList.adapter = mUserAdapter
        binding.usersList.layoutManager = layoutManager

        usersViewModel.users.observe(this) {
            it?.let {
                Log.d("EditDbActivity", it.toString())
                mUserAdapter.submitList(it)
            }
        }
        usersViewModel.event.observe(this) { event ->
            event.consume()?.let {
                if(it is UsersViewModel.OnUserClickEvent) {
                    Log.d("EditDbActivity", "Start edit activity for user_id: " + it.user.id.toString())
                    startEditUserActivity(it.user.id)
                }
            }
        }

        binding.executePendingBindings()


        /*editDbGridView!!.onItemClickListener =
            OnItemClickListener { parent, v, position, id -> startEditUserActivity(id, position) }*/
        binding.editDbFloatingActionButton.setOnClickListener { startEditUserActivity(0) }
    }

    private fun startEditUserActivity(id: Long) {
        val intent = Intent(this@EditDbActivity, EditUserActivity::class.java)
        intent.putExtra(EditUserActivity.ID_KEY, id) //здесь id это реальный id из бд
        startActivity(intent)
    }
}

