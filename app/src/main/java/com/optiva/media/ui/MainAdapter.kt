package com.optiva.media.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.optiva.media.R
import com.optiva.media.core.BaseViewHolder
import com.optiva.media.databinding.MovieItemLayoutBinding
import com.optiva.media.domain.model.Movie
import com.optiva.media.utils.ImageUtils

/**
 * Created by Cesar Conde
 */

class MainAdapter(
    private val mContext: Context,
    private val mItemClickListener: OnClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var mMovielList = listOf<Movie>()

    interface OnClickListener {
        fun onMovieClick(movie: Movie)
        fun onFavItemClick(movie: Movie)
    }

    fun setMovieList(movielList: List<Movie>) {
        this.mMovielList = movielList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)

        val holder = MainViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
            mItemClickListener.onMovieClick(mMovielList[position])
        }

        return holder
    }

    override fun getItemCount(): Int = mMovielList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(mMovielList[position])
        }
    }

    private inner class MainViewHolder(
        val binding: MovieItemLayoutBinding
    ) : BaseViewHolder<Movie>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: Movie) = with(binding) {

            val image = ImageUtils.pickCoverImage(item.attachments?.attachment)

            photoIv.load(image?.let { ImageUtils.imageUrl(it) }) {
                crossfade(true)
            }

            titleTv.text = item.name
            priceTV.text = item.pricingMatrix?.pricingOption?.price.toString() + " €"

            favIv.setImageResource(
                when {
                    item.isFavorite -> R.drawable.ic_delete_white
                    else -> R.drawable.ic_favorite_white
                }
            )

            favIv.setOnClickListener{
                mItemClickListener.onFavItemClick(item)
            }

        }
    }
}