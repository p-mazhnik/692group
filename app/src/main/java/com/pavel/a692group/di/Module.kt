package com.pavel.a692group.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.pavel.a692group.App
import com.pavel.a692group.data.datasource.room.AppDatabase
import com.pavel.a692group.data.repository.AuthRepository
import com.pavel.a692group.data.repository.UsersRepository
import com.pavel.a692group.presentation.login.LoginViewModel
import com.pavel.a692group.presentation.message.MessageViewModel
import com.pavel.a692group.presentation.users.UserDetailViewModel
import com.pavel.a692group.presentation.users.UsersViewModel
import com.pavel.a692group.room.dao.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModules = module {
    single { AuthRepository(get()) }
    single { UsersRepository(get()) }
}

val viewModels = module {
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        MessageViewModel(app= androidApplication() as App, usersRepository = get())
    }
    viewModel {
        UsersViewModel(get())
    }
    viewModel {
        (id: Long) -> UserDetailViewModel(id, get())
    }
}

val databaseModule = module {

    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration()
            // .allowMainThreadQueries()
            .build()
    }


    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
}


val preferenceModule = module {
    fun getSharedPrefs(app: Application): SharedPreferences {
        return app.getSharedPreferences("default",  android.content.Context.MODE_PRIVATE)
    }
    single{
        getSharedPrefs(androidApplication())
    }
}

