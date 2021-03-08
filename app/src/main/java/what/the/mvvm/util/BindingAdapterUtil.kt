package what.the.mvvm.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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
    val adapter = recyclerView?.adapter as MainSearchRecyclerViewAdapter
    data?.let { adapter.addPersonItem(it.doc_url) }
    adapter.notifyDataSetChanged()
}