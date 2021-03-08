package what.the.mvvm

import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import what.the.mvvm.base.BaseKotlinActivity
import what.the.mvvm.databinding.ActivityMainBinding
import what.the.mvvm.viewmodel.MainViewModel

class MainActivity : BaseKotlinActivity<ActivityMainBinding, MainViewModel>() {

    private val TAG = this.javaClass.simpleName

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    private val mainSearchRecyclerViewAdapter: MainSearchRecyclerViewAdapter by inject()

    lateinit var imm: InputMethodManager

    override fun initStartView() {
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.mainActivitySearchTextView.setText("first preview of Android 11")
        binding.mainActivitySearchRecyclerView.run {
            adapter = mainSearchRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(3, 1).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                orientation = StaggeredGridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    @BindingAdapter("bindData")
    fun bindRecyclerView(
        recyclerView: RecyclerView,
        data: String
    ) {
        mainSearchRecyclerViewAdapter.addPersonItem(data)
        mainSearchRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun initDataBinding() {
        viewModel.imageSearchResponseLiveData.observe(this, {
            mainSearchRecyclerViewAdapter.clearList()
            it.documents.forEach { document ->
                mainSearchRecyclerViewAdapter.addImageItem(document.image_url, document.doc_url)
            }
            mainSearchRecyclerViewAdapter.notifyDataSetChanged()
        })
    }

    override fun initAfterBinding() {
        binding.mainActivitySearchButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.mainActivitySearchTextView.windowToken, 0)
            viewModel.getImageSearch(binding.mainActivitySearchTextView.text.toString(), 1, 80)
        }

        binding.mainActivityAddButton.setOnClickListener {
            viewModel.addPersonImage()
        }
    }


}