package com.aryanto.githubfinal.di

import com.aryanto.githubfinal.ui.activity.home.HomeVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeVM(get()) }
}