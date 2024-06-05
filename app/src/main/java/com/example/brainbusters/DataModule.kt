package com.example.brainbusters

import androidx.room.Room
import com.example.brainbusters.data.BrainBusterDatabase
import com.example.brainbusters.data.repositories.UsersRepository
import com.example.brainbusters.ui.viewModels.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            get(),
            BrainBusterDatabase::class.java,
            "database"
        ).build()
    }

    single { UsersRepository(get<BrainBusterDatabase>().usersDAO()) }

    viewModel { UserViewModel(get()) }
}