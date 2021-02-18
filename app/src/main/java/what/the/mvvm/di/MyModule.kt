package what.the.mvvm.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import what.the.mvvm.viewmodel.MainViewModel

/**
 * Created by jongkook on 2021.02.17
 */

/**
 * 모듈 선언 후 변수 저장 방법
 * single  : 앱이 실행되는 동안 계속 유지되는 싱글톤 객체를 생성합니다.
 * factory : 요청할 때마다 매번 새로운 객체를 생성합니다.
 * get()   : 컴포넌트 내에서 알맞은 의존성을 주입 받습니다.
 * ViewModel의 경우 viewModel 키워드로 선언
 */
var viewModelPart = module {
    viewModel {
        MainViewModel()
    }
}

var myDiModule = listOf(viewModelPart)