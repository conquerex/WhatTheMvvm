package what.the.mvvm.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import what.the.mvvm.MainSearchRecyclerViewAdapter
import what.the.mvvm.model.response.ImageSearchResponse.Document

/**
 * Created by jongkook on 2021.03.08
 */
@BindingAdapter("bindData")
fun bindRecyclerView(
    recyclerView: RecyclerView?,
    data: Document?
) {
    Logger.d("* * * BindingAdapter")
    val adapter = recyclerView?.adapter as MainSearchRecyclerViewAdapter
    data?.let { adapter.addPersonItem(it.image_url) }
    adapter.notifyDataSetChanged()
}