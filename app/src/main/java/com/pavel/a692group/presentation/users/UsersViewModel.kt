package com.pavel.a692group.presentation.users

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pavel.a692group.data.repository.UsersRepository
import com.pavel.a692group.room.entity.User
import com.pavel.a692group.utils.Event

class UsersViewModel(
    private val usersRepository: UsersRepository
): ViewModel() {
    val users = Transformations.map(usersRepository.getUsers()){
        it
    }

    private val _event = MutableLiveData<Event<Any>>()
    val event: LiveData<Event<Any>>
        get() = _event

    fun onUserClicked(view: View, user: User) {
        _event.postValue(Event(OnUserClickEvent(user)))
    }

    class OnUserClickEvent(val user: User)
}

