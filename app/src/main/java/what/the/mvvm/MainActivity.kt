package what.the.mvvm

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    // todo : mainSearchRecyclerViewAdapter

    override fun initStartView() {
        binding.mainActivitySearchRecyclerView.run {
            layoutManager = StaggeredGridLayoutManager(3, 1).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                orientation = StaggeredGridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.imageSearchResponseLiveData.observe(this, Observer {
            it.documents.forEach { document ->
                // todo : mainSearchRecyclerViewAdapter
            }
        })
    }

    override fun initAfterBinding() {
        binding.mainActivitySearchButton.setOnClickListener {
            viewModel.getImageSearch(binding.mainActivitySearchTextView.text.toString(), 1, 80)
        }
    }
}