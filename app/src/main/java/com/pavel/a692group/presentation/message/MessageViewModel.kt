package com.pavel.a692group.presentation.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pavel.a692group.App
import com.pavel.a692group.R
import com.pavel.a692group.data.repository.UsersRepository

class MessageViewModel(
    private val app: App,
    private val usersRepository: UsersRepository
): ViewModel() {
    val message = MutableLiveData<String>(app.getString(R.string.hello_text))

    val groups = Transformations.map(usersRepository.getGroups()) {
        it
    }
}

