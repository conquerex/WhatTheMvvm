package what.the.mvvm

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import what.the.mvvm.databinding.ItemMainImageBinding

/**
 * Created by jongkook on 2021.02.24
 */

class MainSearchRecyclerViewAdapter :
    RecyclerView.Adapter<MainSearchRecyclerViewAdapter.ImageHolder>() {

    private val TAG = this.javaClass.simpleName

    data class ImageItem(var imageUrl: String, var documentUrl: String)

    private val imageItemList = ArrayList<ImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding =
            ItemMainImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }

    override fun getItemCount() = imageItemList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        (holder as? ImageHolder)?.onBind(imageItemList[position])
    }

    fun clearList() {
        imageItemList.clear()
    }

    fun addImageItem(imageUrl: String, documentUrl: String) {
        imageItemList.add(ImageItem(imageUrl, documentUrl))
    }

    fun addPersonItem(url: String) {
        imageItemList.add(ImageItem("", url))
    }

    class ImageHolder(private val binding: ItemMainImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ImageItem) {
            itemView.run {
                if (item.imageUrl.length > 5) {
                    Picasso.get()
                        .load(item.imageUrl)
                        .error(R.drawable.ic_error_64)
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .into(binding.itemMainImageView)
                } else {
                    Picasso.get()
                        .load(R.drawable.ic_emoji_people_64)
                        .error(R.drawable.ic_emoji_people_64)
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .into(binding.itemMainImageView)
                }

                binding.itemMainImageView.setOnClickListener {
                    if (item.documentUrl.length > 5) {
                        ContextCompat.startActivity(
                            context,
                            Intent(Intent.ACTION_VIEW, Uri.parse(item.documentUrl)),
                            null
                        )
                    } else {
                        Toast.makeText(context, "... Empty ...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}