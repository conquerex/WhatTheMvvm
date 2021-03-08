package what.the.mvvm.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import what.the.mvvm.MainSearchRecyclerViewAdapter

/**
 * Created by jongkook on 2021.03.08
 */
@BindingAdapter("bindData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: String?
) {
    Logger.d("* * * BindingAdapter")
    val adapter = recyclerView.adapter as MainSearchRecyclerViewAdapter
    data?.let { adapter.addPersonItem(it) }
    adapter.notifyDataSetChanged()
}