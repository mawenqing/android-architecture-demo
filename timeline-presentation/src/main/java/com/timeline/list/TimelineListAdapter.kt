package com.timeline.list

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.core.timeline.STATUS_SOLD_OUT
import com.timeline.R
import kotlinx.android.synthetic.main.item_timeline.view.*
import javax.inject.Inject

/**
 * @author mawenqing.
 */
class TimelineListAdapter @Inject constructor() : RecyclerView.Adapter<TimelineViewHolder>() {
    private var timeline = listOf<TimelineItemModel>()

    fun updateData(timeline: List<TimelineItemModel>) {
        this.timeline = timeline
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        return TimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_timeline,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = timeline.count()

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bind(timeline[position])
    }
}

class TimelineViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(model: TimelineItemModel) {
        view.apply {
            likeCount.text = model.likeCount.toString()
            commentCount.text = model.commentCount.toString()
            price.text = view.context.getString(R.string.price, model.price)
            soldOutBadge.visibility = if (model.status == STATUS_SOLD_OUT) VISIBLE else GONE
            Glide.with(view)
                .load(model.photo)
                .into(view.poster)
        }
    }
}
