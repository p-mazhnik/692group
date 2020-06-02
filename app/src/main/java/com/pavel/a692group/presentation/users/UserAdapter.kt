package com.pavel.a692group.presentation.users

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pavel.a692group.databinding.UserItemBinding
import com.pavel.a692group.data.entity.User

/**
 * Created by Pavel Mazhnik on 03.03.19.
 * Description:
 * адаптер для отображния списка пользователей в RecyclerView в EditDbActivity
 */
class UserAdapter(private val viewLifecycleOwner: LifecycleOwner, private val vm: UsersViewModel) :
    ListAdapter<User, UserAdapter.UserListHolder>(
        UserDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        Log.d("UserAdapter", "onCreateViewHolder")
        val binding =
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return UserListHolder(
            vm,
            binding
        )
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        val user = getItem(position)
        Log.d("UserAdapter", user.toString())

        if (user != null) {
            holder.bind(user)
        } else {
            holder.clear()
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(v1: User, v2: User): Boolean {
            return v1.id == v2.id
        }

        override fun areContentsTheSame(v1: User, v2: User): Boolean {
            return v1 == v2
        }
    }

    class UserListHolder(private val vm: UsersViewModel, private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.user = user
            binding.usersViewModel = vm
            binding.executePendingBindings()
        }

        fun clear() {
        }
    }
}

