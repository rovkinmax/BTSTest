package ru.rovkinmax.bts.feature.photos.presentation.search.view.adapter

import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*
import ru.rovkinmax.bts.R
import ru.rovkinmax.bts.feature.global.presentation.view.adapter.LoadingAdapter
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.fieldsadapter.matcher.FieldViewHolder

class PhotosAdapter(private val listener: ((PhotoEntity) -> Unit)) : LoadingAdapter<PhotoEntity>() {

    init {
        addXmlLayouter(R.layout.item_photo) {
            matcher { it is PhotoEntity }
            creator { view, viewGroup -> PhotoViewHolder(view, viewGroup) }
        }
    }

    private inner class PhotoViewHolder(itemView: View, root: ViewGroup) : FieldViewHolder<PhotoEntity>(itemView, root) {

        init {
            itemView.setOnClickListener { data?.let { listener.invoke(it) } }
        }

        override fun performBind(data: PhotoEntity) {
            Picasso.get()
                    .load(data.urlThumbnail)
                    .into(itemView.ivImage)
            itemView.tvName.text = data.title
        }
    }
}