package what.the.mvvm.util

import androidx.lifecycle.MutableLiveData


/**
 * Created by jongkook on 2021.03.08
 */
class ListLiveData<T> : MutableLiveData<ArrayList<T?>?>() {

    fun add(item: T) {
        val items = value
        items!!.add(item)
        value = items
    }

    fun addAll(list: List<T?>?) {
        val items = value
        items!!.addAll(list!!)
        value = items
    }

    fun clear(notify: Boolean) {
        val items = value
        items!!.clear()
        if (notify) {
            value = items
        }
    }

    fun remove(item: T) {
        val items = value
        items!!.remove(item)
        value = items
    }

    fun notifyChange() {
        val items = value
        value = items
    }

    init {
        value = ArrayList()
    }
}