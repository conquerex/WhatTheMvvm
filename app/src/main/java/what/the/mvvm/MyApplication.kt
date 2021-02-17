package what.the.mvvm

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import what.the.mvvm.di.myDiModule

/**
 * Created by jongkook on 2021.02.17
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(myDiModule)
        }
    }
}