package what.the.mvvm.model.service

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import what.the.mvvm.model.response.ImageSearchResponse

/**
 * Created by jongkook on 2021.02.19
 */
interface KakaoSearchService {
    @GET("/v2/search/image")
    fun searchImage(
        @Header("Authorization") auth:String,
        @Query("query") query:String,
        @Query("sort") sort:String,
        @Query("page") page:Int,
        @Query("size") size:Int
    ): Single<ImageSearchResponse>
}