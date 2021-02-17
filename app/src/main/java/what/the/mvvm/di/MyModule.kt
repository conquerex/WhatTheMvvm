package what.the.mvvm.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import what.the.mvvm.viewmodel.MainViewModel

/**
 * Created by jongkook on 2021.02.17
 */
var viewModelPart = module {
    viewModel {
        MainViewModel()
    }
}

var myDiModule = listOf(viewModelPart)