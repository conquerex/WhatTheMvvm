package what.the.mvvm

import org.koin.androidx.viewmodel.ext.android.viewModel
import what.the.mvvm.base.BaseKotlinActivity
import what.the.mvvm.databinding.ActivityMainBinding
import what.the.mvvm.viewmodel.MainViewModel

class MainActivity : BaseKotlinActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    // todo : 아래 주석은 블로그 포스팅 후 제거 예정
    // Expression 'viewModel' of type 'MainViewModel' cannot be invoked as a function. The function 'invoke()' is not found
    // 해결책 : import org.koin.androidx.viewmodel.ext.android.viewModel
    override val viewModel: MainViewModel by viewModel()

    override fun initStartView() {
        TODO("Not yet implemented")
    }

    override fun initDataBinding() {
        TODO("Not yet implemented")
    }

    override fun initAfterBinding() {
        TODO("Not yet implemented")
    }
}