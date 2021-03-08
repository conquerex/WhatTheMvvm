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

    val imageSearchResponseLiveData: LiveData<ArrayList<Document>>
        get() = _imageSearchResponseLiveData

    private val _personData = MutableLiveData<ArrayList<Document>>()

    val personData: LiveData<ArrayList<Document>>
        get() = _personData

    fun addPersonImage() {
        val doc = Document("", "", "", 0, 0, "", "https://devvkkid.tistory.com/", "")

        // LiveData 내부에 arrayList를 담고 싶다면 아래처럼
        _personData.value = arrayListOf(doc)
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