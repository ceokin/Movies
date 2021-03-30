package com.optiva.media.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Cesar Conde
 */

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}