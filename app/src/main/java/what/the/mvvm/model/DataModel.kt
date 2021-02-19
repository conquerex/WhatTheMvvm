package what.the.mvvm.model

import io.reactivex.rxjava3.core.Single
import what.the.mvvm.model.enum.KakaoSearchSortEnum
import what.the.mvvm.model.response.ImageSearchResponse

/**
 * Created by jongkook on 2021.02.19
 */
interface DataModel {
    fun getData(
        query: String,
        sort: KakaoSearchSortEnum,
        page: Int,
        size: Int
    ): Single<ImageSearchResponse>
}