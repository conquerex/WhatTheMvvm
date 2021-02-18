package what.the.mvvm

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import what.the.mvvm.di.myDiModule

/**
 * Created by jongkook on 2021.02.17
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 모듈 등록
        startKoin {
            // Koin이 로그를 남기는 레벨을 지정 - Level.INFO by default
            androidLogger()
            // Koin은 코틀린 환경이라면 어디든 동작한다
            // 코틀린 환경의 안드로이드 앱 개발시 androidContext로 Context를 주입시킨다
            // inject Android context
            androidContext(this@MyApplication)
            // 선언한 모듈 지정
            modules(myDiModule)
        }
    }
}