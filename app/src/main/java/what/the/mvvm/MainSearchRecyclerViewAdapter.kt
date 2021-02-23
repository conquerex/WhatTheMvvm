package what.the.mvvm

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import what.the.mvvm.databinding.ItemMainImageBinding

/**
 * Created by jongkook on 2021.02.24
 */

class MainSearchRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    data class ImageItem(var imageUrl: String, var documentUrl: String)

    class ImageHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_main_image, parent, false)
    ) {
        private val binding = ItemMainImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        fun onBind(item: ImageItem) {

            itemView.run {
                Picasso.get().load(item.imageUrl)
                    .placeholder(R.drawable.ic_image_black_24dp).into(binding.itemMainImageView)

                binding.itemMainImageView.setOnClickListener {
                    ContextCompat.startActivity(
                        context,
                        Intent(Intent.ACTION_VIEW, Uri.parse(item.documentUrl)),
                        null
                    )
                }
            }
        }
    }

    private val imageItemList = ArrayList<ImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageHolder(parent)

    override fun getItemCount() = imageItemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ImageHolder)?.onBind(imageItemList[position])
    }

    fun addImageItem(imageUrl: String, documentUrl: String) {
        imageItemList.add(ImageItem(imageUrl, documentUrl))
    }

}