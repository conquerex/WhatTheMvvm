package what.the.mvvm.model

import io.reactivex.rxjava3.core.Single
import what.the.mvvm.BuildConfig
import what.the.mvvm.model.enum.KakaoSearchSortEnum
import what.the.mvvm.model.response.ImageSearchResponse
import what.the.mvvm.model.service.KakaoSearchService

/**
 * Created by jongkook on 2021.02.19
 */
class DataModelImpl(private val service: KakaoSearchService) : DataModel {
    override fun getData(
        query: String,
        sort: KakaoSearchSortEnum,
        page: Int,
        size: Int
    ): Single<ImageSearchResponse> {
        return service.searchImage(
            auth = "KakaoAK ${BuildConfig.KAKAO_API_KEY}",
            query = query,
            sort = sort.sort,
            page = page,
            size = size
        )
    }
}