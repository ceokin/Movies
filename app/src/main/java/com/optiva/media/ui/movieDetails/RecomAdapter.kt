package com.optiva.media.ui.movieDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.optiva.media.core.BaseViewHolder
import com.optiva.media.databinding.RecomItemLayoutBinding
import com.optiva.media.domain.model.VideoRecommendation
import com.optiva.media.utils.ImageUtils

/**
 * Created by Cesar Conde
 */

class RecomAdapter(
    private val mContext: Context,
    private val mItemClickListener: OnClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var mRecomList = listOf<VideoRecommendation>()

    interface OnClickListener {
        fun onRecomClick(recom: VideoRecommendation)
    }

    fun setRecomList(recomList: List<VideoRecommendation>?) {
        this.mRecomList = recomList!!
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = RecomItemLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)

        val holder = RecomViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
            mItemClickListener.onRecomClick(mRecomList[position])
        }

        return holder
    }

    override fun getItemCount(): Int = mRecomList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is RecomViewHolder -> holder.bind(mRecomList[position])
        }
    }

    private inner class RecomViewHolder(
        val binding: RecomItemLayoutBinding
    ) : BaseViewHolder<VideoRecommendation>(binding.root) {
        override fun bind(item: VideoRecommendation) = with(binding) {

            val image = ImageUtils.pickRecomImage(item.images?.image)

            imageRecomIv.load(image?.let { ImageUtils.imageUrl(it) }) {
                crossfade(true)
            }

            titleRecomTv.text = item.name
            ratingTV.text = item.rating.toString()
        }
    }
}