package what.the.mvvm.model.response

/**
 * Created by jongkook on 2021.02.19
 */
data class ImageSearchResponse(
    var documents: ArrayList<Document>,
    var meta: Meta
) {
    data class Document(
        var collection: String,
        var thumbnail_url: String,
        var image_url: String,
        var width: Int,
        var height: Int,
        var display_sitename: String,
        var doc_url: String,
        var datetime: String
    )

    data class Meta(
        var total_count: Int,
        var pageable_count: Int,
        var is_end: Boolean
    )
}