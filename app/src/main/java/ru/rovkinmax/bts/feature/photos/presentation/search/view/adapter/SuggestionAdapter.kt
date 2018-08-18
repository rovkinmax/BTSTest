package ru.rovkinmax.bts.feature.photos.presentation.search.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.rovkinmax.bts.R
import ru.rovkinmax.fieldsadapter.FieldsAdapter
import ru.rovkinmax.fieldsadapter.matcher.FieldViewHolder

class SuggestionAdapter(private val listener: ((String) -> Unit)) : FieldsAdapter<String>() {

    init {
        addXmlLayouter(R.layout.item_suggestion) {
            matcher { it is String }
            creator { view, viewGroup -> SuggestionViewHolder(view, viewGroup) }
        }
    }

    private inner class SuggestionViewHolder(itemView: View, root: ViewGroup) : FieldViewHolder<String>(itemView, root) {
        init {
            itemView.setOnClickListener { data?.let { listener.invoke(it) } }
        }

        override fun performBind(data: String) {
            (itemView as? TextView)?.text = data
        }
    }
}