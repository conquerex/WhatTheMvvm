package what.the.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import what.the.mvvm.base.BaseKotlinViewModel
import what.the.mvvm.model.DataModel
import what.the.mvvm.model.enum.KakaoSearchSortEnum
import what.the.mvvm.model.response.ImageSearchResponse.Document

/**
 * Created by jongkook on 2021.02.17
 */
class MainViewModel(private val model: DataModel) : BaseKotlinViewModel() {

    private val TAG = this.javaClass.simpleName

    private val _imageSearchResponseLiveData = MutableLiveData<ArrayList<Document>>()

    val personData = MutableLiveData<String>()

    val imageSearchResponseLiveData: LiveData<ArrayList<Document>>
        get() = _imageSearchResponseLiveData

    fun addPersonImage() {
        Logger.d("* * * addPersonImage")
//        personData.value = "https://devvkkid.tistory.com/"
        _imageSearchResponseLiveData.value?.add(Document("", "", "", 0, 0, "", "", ""))

    }

    fun getImageSearch(query: String, page: Int, size: Int) {
        addDisposable(
            model.getData(query, KakaoSearchSortEnum.Accuracy, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        if (documents.size > 0) {
                            Log.d(TAG, "documents : $documents")
                            _imageSearchResponseLiveData.postValue(this.documents)
                        }
                        Log.d(TAG, "meta : $meta")
                    }
                }, {
                    Log.d(TAG, "response error, message : ${it.message}")
                })
        )
    }
}