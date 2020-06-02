package com.pavel.a692group.presentation.users

import androidx.lifecycle.*
import com.pavel.a692group.data.repository.UsersRepository
import com.pavel.a692group.data.entity.User
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val userId: Long,
    private val usersRepository: UsersRepository
) : ViewModel() {

    val inputId = MutableLiveData<String>()
    val inputName = MutableLiveData<String>()

    fun init() {
        viewModelScope.launch {
            val user = usersRepository.getUserById(userId)
            inputId.postValue(user?.id?.toString() ?: "")
            inputName.postValue(user?.name)
        }
    }

    fun save(){
        viewModelScope.launch {
            usersRepository.insert(
                User(
                    inputId.value!!,
                    inputName.value!!
                )
            )
        }
    }

    fun delete(){
        viewModelScope.launch {
            val user = usersRepository.getUserById(userId)
            if (user != null) {
                usersRepository.delete(user)
            }
        }
    }

}

