package what.the.mvvm.di

import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import what.the.mvvm.MainSearchRecyclerViewAdapter
import what.the.mvvm.model.DataModel
import what.the.mvvm.model.DataModelImpl
import what.the.mvvm.model.service.KakaoSearchService
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
        MainViewModel(get())
    }
}

var modelPart = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
}

var retrofitPart = module {
    single<KakaoSearchService> {

        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger {
                try {
                    JSONObject(it)
                    Logger.t("INTERCEPTOR").json(it)
                } catch (error: JSONException) {
                    Logger.t("INTERCEPTOR").i(it)
                }
            }
        ).setLevel(HttpLoggingInterceptor.Level.BODY)).build()

        Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(KakaoSearchService::class.java)
    }
}

var adapterPart = module {
    factory {
        MainSearchRecyclerViewAdapter()
    }
}

var myDiModule = listOf(viewModelPart, modelPart, retrofitPart, adapterPart)